package ch.ebu.cpa;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import ch.ebu.cpa.fragments.AssociateDialogFragment;
import ch.ebu.cpa.model.CpaClientIdentity;
import ch.ebu.cpa.model.CpaClientInfo;
import ch.ebu.cpa.model.CpaClientToken;
import ch.ebu.cpa.model.CpaError;
import ch.ebu.cpa.model.CpaUserCode;
import ch.ebu.cpa.model.CpaUserToken;
import ch.ebu.cpa.utils.PrefsUtils;
import ch.ebu.cpa.webservice.CpaService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Copyright (c) European Broadcasting Union. All rights reserved.
 * <p/>
 * Licence information is available from the LICENCE file.
 */
public class CpaProvider {

    private static final String GRANT_TYPE_CREDENTIALS = "http://tech.ebu.ch/cpa/1.0/client_credentials";
    private static final String GRANT_TYPE_DEVICE_CODE = "http://tech.ebu.ch/cpa/1.0/device_code";

    private Activity activity;

    private String endPoint;
    private String appVersion;
    private String appName;
    private String appPackage;

    private RestAdapter adapter;
    private CpaService cpaService;

    private CpaUserCode cpaUserCode;

    public CpaProvider(@NonNull Activity activity, @NonNull String endPoint) {
        this.activity = activity;
        this.endPoint = endPoint;
        initRestAdapter();
        initClientInfo();
    }

    private void initRestAdapter() {
        adapter = new RestAdapter.Builder().setEndpoint(endPoint).build();
        cpaService = adapter.create(CpaService.class);
    }

    private void initClientInfo() {
        // Get version number and app name from package manager
        appVersion = "1.0";
        appName = "Cpa Client";
        appPackage = activity.getPackageName();
        PackageManager manager = activity.getPackageManager();
        try {

            // Version number
            PackageInfo packageInfo = manager.getPackageInfo(appPackage, 0);
            appVersion = packageInfo.versionName;

            // Application name
            ApplicationInfo appInfo = manager.getApplicationInfo(appPackage, 0);
            appName = manager.getApplicationLabel(appInfo).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getClientToken(@NonNull final String domain, @NonNull final CpaProviderClientTokenListener listener) {
        // Try to retrieve client token from shared preferences
        CpaClientToken token = PrefsUtils.retrieveClientToken(activity, domain);
        if (token != null) {
            listener.success(token);
            return;
        }

        // Create new CpaClientIdentity for requesting client registration
        CpaClientInfo info = new CpaClientInfo(appName, appPackage, appVersion);

        // Register new client
        cpaService.clientRegistration(info, new Callback<CpaClientIdentity>() {
            @Override
            public void success(CpaClientIdentity identity, Response response) {

                // Persist client identity for future use
                PrefsUtils.persistIdentity(activity, identity);

                // Set requested domain to current CpaClientIdentity
                identity.setDomain(domain);

                // Set grant_type to current CpaClientIdentity for request a token
                identity.setGrantType(GRANT_TYPE_CREDENTIALS);

                // Send request to get token for current CpaClientIdentity
                cpaService.clientToken(identity, new Callback<CpaClientToken>() {
                    @Override
                    public void success(CpaClientToken cpaClientToken, Response response) {
                        // Notify listener with new token
                        listener.success(cpaClientToken);
                        PrefsUtils.persistClientToken(activity, cpaClientToken, domain);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        switch (error.getKind()) {

                            case NETWORK:
                                listener.failure(new CpaError(CpaError.Kind.NETWORK_ERROR));
                                break;

                            case HTTP:
                                if (error.getResponse().getStatus() == 400) {
                                    String json = new String(((TypedByteArray) error.getResponse().getBody()).getBytes());
                                    if (json.contains("invalid_client")) {
                                        listener.failure(new CpaError(CpaError.Kind.INVALID_CLIENT));
                                    } else {
                                        listener.failure(new CpaError(CpaError.Kind.INVALID_REQUEST));
                                    }
                                }
                                break;

                            default:
                                listener.failure(new CpaError(CpaError.Kind.UNKNOWN));
                                break;
                        }
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                switch (error.getKind()) {

                    case NETWORK:
                        listener.failure(new CpaError(CpaError.Kind.NETWORK_ERROR));
                        break;

                    case HTTP:
                        listener.failure(new CpaError(CpaError.Kind.INVALID_REQUEST));
                        break;

                    default:
                        listener.failure(new CpaError(CpaError.Kind.UNKNOWN));
                        break;

                }
            }
        });
    }

    public void getUserToken(@NonNull final String domain, @NonNull final CpaProviderUserTokenListener listener) {

        // Try to retrieve client token from shared preferences
        CpaUserToken token = PrefsUtils.retrieveUserToken(activity, domain);
        if (token != null) {
            listener.success(token);
            return;
        }

        // Create new CpaClientIdentity for requesting client registration
        CpaClientInfo info = new CpaClientInfo(appName, appPackage, appVersion);

        // Register new client
        cpaService.clientRegistration(info, new Callback<CpaClientIdentity>() {
            @Override
            public void success(final CpaClientIdentity identity, Response response) {

                // Persist client identity for future use
                PrefsUtils.persistIdentity(activity, identity);

                // Set requested domain to current CpaClientIdentity
                identity.setDomain(domain);

                cpaService.userCodeRequest(identity, new Callback<CpaUserCode>() {
                    @Override
                    public void success(CpaUserCode userCode, Response response) {

                        cpaUserCode = userCode;

                        identity.setGrantType(GRANT_TYPE_DEVICE_CODE);
                        identity.setDeviceCode(cpaUserCode.getDeviceCode());

                        sendUserTokenRequest(domain, identity, listener);

                    }

                    @Override
                    public void failure(RetrofitError error) {

                        switch (error.getKind()) {

                            case NETWORK:
                                listener.failure(new CpaError(CpaError.Kind.NETWORK_ERROR));
                                break;

                            case HTTP:
                                if (error.getResponse().getStatus() == 400) {
                                    String json = new String(((TypedByteArray) error.getResponse().getBody()).getBytes());
                                    if (json.contains("invalid_client")) {
                                        listener.failure(new CpaError(CpaError.Kind.INVALID_CLIENT));
                                    } else {
                                        listener.failure(new CpaError(CpaError.Kind.INVALID_REQUEST));
                                    }
                                }
                                break;

                            default:
                                listener.failure(new CpaError(CpaError.Kind.UNKNOWN));
                                break;
                        }

                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {

                switch (error.getKind()) {

                    case NETWORK:
                        listener.failure(new CpaError(CpaError.Kind.NETWORK_ERROR));
                        break;

                    case HTTP:
                        listener.failure(new CpaError(CpaError.Kind.INVALID_REQUEST));
                        break;

                    default:
                        listener.failure(new CpaError(CpaError.Kind.UNKNOWN));
                        break;

                }
            }
        });
    }

    public void refreshClientToken(@NonNull final String domain, @NonNull final CpaProviderClientTokenListener listener) {
        CpaClientIdentity identity = PrefsUtils.retrieveIdentity(activity);
        if (identity == null) {
            getClientToken(domain, listener);
        } else {

            identity.setGrantType(GRANT_TYPE_CREDENTIALS);
            identity.setDomain(domain);

            cpaService.refreshClientToken(identity, new Callback<CpaClientToken>() {
                @Override
                public void success(CpaClientToken cpaClientToken, Response response) {
                    // Notify listener with new token
                    listener.success(cpaClientToken);
                    PrefsUtils.persistClientToken(activity, cpaClientToken, domain);
                }

                @Override
                public void failure(RetrofitError error) {
                    listener.failure(new CpaError(CpaError.Kind.EXPIRED));
                }
            });
        }
    }

    public void refreshUserToken(@NonNull final String domain, @NonNull final CpaProviderUserTokenListener listener) {

        CpaClientIdentity identity = PrefsUtils.retrieveIdentity(activity);
        if (identity == null) {
            getUserToken(domain, listener);
        } else {

            identity.setGrantType(GRANT_TYPE_CREDENTIALS);
            identity.setDomain(domain);

            cpaService.refreshUserToken(identity, new Callback<CpaUserToken>() {
                @Override
                public void success(CpaUserToken cpaUserToken, Response response) {
                    listener.success(cpaUserToken);
                    PrefsUtils.persistUserToken(activity, cpaUserToken, domain);
                }

                @Override
                public void failure(RetrofitError error) {
                    listener.failure(new CpaError(CpaError.Kind.EXPIRED));
                }
            });

        }

    }

    private void sendUserTokenRequest(final String domain, final CpaClientIdentity identity, final CpaProviderUserTokenListener listener) {

        cpaService.userToken(identity, new Callback<CpaUserToken>() {
            @Override
            public void success(CpaUserToken cpaUserToken, Response response) {

                if (response.getStatus() == 202) {

                    StringBuilder builder = new StringBuilder();
                    builder.append(cpaUserCode.getVerificationUri());
                    builder.append("/");
                    builder.append("?user_code=");
                    builder.append(cpaUserCode.getUserCode());
                    builder.append("&redirect_uri=");
                    builder.append(AssociateDialogFragment.REDIRECT_URI);

                    // Instantiate a new fragment for associate user
                    AssociateDialogFragment fragment = AssociateDialogFragment.newInstance(builder.toString());

                    fragment.setAssociationCompleteListener(new AssociateDialogFragment.AssociationCompleteListener() {
                        @Override
                        public void onAssociationComplete() {
                            sendUserTokenRequest(domain, identity, listener);
                        }

                        @Override
                        public void onAssociationDenied() {
                            listener.failure(new CpaError(CpaError.Kind.USER_DENIED));
                        }

                    });

                    fragment.show(activity.getFragmentManager(), "dialog");
                } else {
                    listener.success(cpaUserToken);
                    PrefsUtils.persistUserToken(activity, cpaUserToken, domain);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                switch (error.getKind()) {

                    case NETWORK:
                        listener.failure(new CpaError(CpaError.Kind.NETWORK_ERROR));
                        break;

                    case HTTP:
                        if (error.getResponse().getStatus() == 400) {
                            String json = new String(((TypedByteArray) error.getResponse().getBody()).getBytes());
                            if (json.contains("invalid_client")) {
                                listener.failure(new CpaError(CpaError.Kind.INVALID_CLIENT));
                            } else if(json.contains("invalid_request")) {
                                listener.failure(new CpaError(CpaError.Kind.INVALID_REQUEST));
                            } else {
                                listener.failure(new CpaError(CpaError.Kind.SLOW_DOWN));
                            }
                        }
                        break;

                    default:
                        listener.failure(new CpaError(CpaError.Kind.UNKNOWN));
                        break;
                }
            }
        });
    }

    public void setAdapterLogLevel(RestAdapter.LogLevel level) {
        adapter.setLogLevel(level);
    }

    public interface CpaProviderClientTokenListener {

        void success(CpaClientToken token);

        void failure(CpaError error);

    }

    public interface CpaProviderUserTokenListener {

        void success(CpaUserToken token);

        void failure(CpaError error);

    }

}
