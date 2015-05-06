package ch.ebu.cpa.fragments;


import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ch.ebu.cpa.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AssociateDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AssociateDialogFragment extends DialogFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String URL = "ch.ebu.cpa.fragments.url";
    public static final String REDIRECT_URI = "cpacred://verification";

    private String url;

    private AssociationCompleteListener mListener;
    private WebView webView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param url the url to load.
     * @return A new instance of fragment AssociateDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AssociateDialogFragment newInstance(String url) {
        AssociateDialogFragment fragment = new AssociateDialogFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    public AssociateDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(URL);
        } else {
            url = savedInstanceState.getString(URL, "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.associate_dialog_fragment, container, false);
        webView = (WebView) view.findViewById(R.id.associate_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith(REDIRECT_URI)) {
                    dismissCurrent();
                } else {
                    webView.loadUrl(url);
                }

                return true;
            }
        });
        webView.loadUrl(url);
        return view;
    }

    public void setAssociationCompleteListener( AssociationCompleteListener listener){
        mListener = listener;
    }

    private void dismissCurrent(){
        if (mListener != null){
            mListener.onAssociationComplete();
        }
        dismiss();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(URL, url);
    }

    public interface AssociationCompleteListener{
        void onAssociationComplete();
    }

}
