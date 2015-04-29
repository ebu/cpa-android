package ch.ebu.cpa;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import ch.ebu.cpa.model.CpaClientIdentity;
import ch.ebu.cpa.model.CpaClientInfo;
import ch.ebu.cpa.model.CpaClientToken;
import ch.ebu.cpa.model.CpaError;
import ch.ebu.cpa.utils.PrefsUtils;
import ch.ebu.cpa.webservice.CpaService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 *  Copyright (c) European Broadcasting Union. All rights reserved.
 *
 *  Licence information is available from the LICENCE file.
 */
public class CpaProvider {

    private Context context;

    private String endPoint;

    private static final String GRANT_TYPE_CREDENTIALS = "http://tech.ebu.ch/cpa/1.0/client_credentials";

    private RestAdapter adapter;

    private CpaService cpaService;

    private String appVersion;

    private String appName;

    private String appPackage;

    public CpaProvider(Context context, String endPoint) {
        this.context = context;
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
        appPackage = context.getPackageName();
        PackageManager manager = context.getPackageManager();
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

    public void getClientToken(final String domain, final CpaProdiverTokenListener listener) {

        // Try to retreive client token from shared preferences
        CpaClientToken token = PrefsUtils.retreiveClientToken(context, domain);
        if (token != null){
            listener.success(token);
            return;
        }

        // Create new CpaClientIdentity for requesting client registration
        CpaClientInfo info = new CpaClientInfo(appName, appPackage, appVersion);

        cpaService.clientRegistration(info, new Callback<CpaClientIdentity>() {
            @Override
            public void success(CpaClientIdentity identity, Response response) {

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
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public void setAdapterLogLevel(RestAdapter.LogLevel level) {
        adapter.setLogLevel(level);
    }

    public interface CpaProdiverTokenListener {

        void success(CpaClientToken token);

        void failure(CpaError error);

    }

}
