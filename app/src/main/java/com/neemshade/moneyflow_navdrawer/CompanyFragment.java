package com.neemshade.moneyflow_navdrawer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.neemshade.moneyflow_navdrawer.data.Company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tejesvini on 22-12-16.
 */

public class CompanyFragment extends Fragment {

    public CompanyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_company, container, false);
        TextView textView = (TextView)view.findViewById(R.id.section_label);
        textView.setText("Company");

        ListView listView = (ListView)view.findViewById(R.id.companyList) ;
        final List<Company> companies = Company.fetchCompanies();
        final List<String> companyNames = new ArrayList<String>();



        for (Company company : companies) {
            companyNames.add(company.getName().getName());
        }
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,companyNames);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Company.setSelectedCompany(companies.get(i));

                //move to outstanding fragment
                Fragment fragment =null;
                fragment = new OutstandingFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();

            }
        });

        return view;

    }
}