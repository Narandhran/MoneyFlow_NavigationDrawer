package com.neemshade.moneyflow_navdrawer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {


    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_help, container, false);
        TextView textView = (TextView)view.findViewById(R.id.help);

        textView.setText(Html.fromHtml(getString(R.string.help)));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }

}
