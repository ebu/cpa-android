package ch.ebu.cpa.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import ch.ebu.cpa.CpaProvider;
import ch.ebu.cpa.model.CpaClientToken;
import ch.ebu.cpa.model.CpaError;
import ch.ebu.cpa.model.CpaUserToken;
import ch.ebu.cpa.utils.PrefsUtils;
import retrofit.RestAdapter;


public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.token_text_view)
    TextView mTokenTextView;
    @InjectView(R.id.user_token_switch)
    Switch mUserTokenSwitch;
    @InjectView(R.id.domain_spinner)
    Spinner mDomainSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.domains, android.R.layout.simple_dropdown_item_1line);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mDomainSpinner.setAdapter(adapter);

    }

    @OnCheckedChanged(R.id.user_token_switch)
    public void resetToken() {
        PrefsUtils.clearAllToken(this);
        mTokenTextView.setText(R.string.token);
    }

    @OnClick(R.id.get_token_button)
    public void getToken() {
        CpaProvider provider = new CpaProvider(this, "https://cpa.rts.ch");
        if (mUserTokenSwitch.isChecked()) {
            provider.setAdapterLogLevel(RestAdapter.LogLevel.FULL);
            provider.getUserToken((String) mDomainSpinner.getSelectedItem(), new MyCpaProviderUserTokenListener());
        } else {
            provider.setAdapterLogLevel(RestAdapter.LogLevel.FULL);
            provider.getClientToken((String) mDomainSpinner.getSelectedItem(), new MyCpaProviderClientTokenListener());
        }
    }

    @OnClick(R.id.refresh_token_button)
    public void refreshToken() {
        CpaProvider provider = new CpaProvider(this, "https://cpa.rts.ch");
        if (mUserTokenSwitch.isChecked()) {
            provider.setAdapterLogLevel(RestAdapter.LogLevel.FULL);
            provider.refreshUserToken((String) mDomainSpinner.getSelectedItem(), new MyCpaProviderUserTokenListener());
        } else {
            provider.setAdapterLogLevel(RestAdapter.LogLevel.FULL);
            provider.refreshClientToken((String) mDomainSpinner.getSelectedItem(), new MyCpaProviderClientTokenListener());
        }
    }

    public class MyCpaProviderClientTokenListener implements CpaProvider.CpaProviderClientTokenListener {

        @Override
        public void success(CpaClientToken token) {
            mTokenTextView.setText(token.getAccessToken());
        }

        @Override
        public void failure(CpaError error) {

        }
    }

    public class MyCpaProviderUserTokenListener implements CpaProvider.CpaProviderUserTokenListener {

        @Override
        public void success(CpaUserToken token) {
            mTokenTextView.setText(token.getAccessToken());
        }

        @Override
        public void failure(CpaError error) {

        }
    }

}
