package com.neemshade.moneyflow_navdrawer;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.neemshade.moneyflow_navdrawer.data.Company;
import com.neemshade.moneyflow_navdrawer.data.Party;
import com.neemshade.moneyflow_navdrawer.data.Transaction;
import com.neemshade.moneyflow_navdrawer.services.TransactionIncluder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransFragment extends Fragment implements View.OnFocusChangeListener {

    EditText amtTransInput,transDate,dueDate,productInfo,productQuandity,description;
    private int mYear, mMonth, mDay;
    Button submitBtnTrans;
    Switch transSwitch;
    public TransFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_trans, container, false);

        transSwitch = (Switch)view.findViewById(R.id.sell_buy_switch);
        transSwitch.setText("Seller");

        submitBtnTrans = (Button)view.findViewById(R.id.submitBtn_trans);
        submitBtnTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productQuandity = (EditText) getView().findViewById(R.id.transquandityInput);
                amtTransInput = (EditText) getView().findViewById(R.id.transAmtInput);
                if((productQuandity.getText().toString().trim().equals("")) || (amtTransInput.getText().toString().trim().equals(""))){
                    productQuandity.getText().clear();
                    amtTransInput.getText().clear();
                    productQuandity.setError("Quandity is Required");
                    amtTransInput.setError("Price is Required");
                }
                else
                submitTransactionData();
            }
        });
        transSwitch = (Switch)view.findViewById(R.id.sell_buy_switch);
        transSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(transSwitch.isChecked()) transSwitch.setText("Buyer");
                else transSwitch.setText("Seller");
            }
        });

        final Spinner partySpinner = (Spinner) view.findViewById(R.id.partyListSpinner_Trans) ;

        Company selectedCompany = Company.getSelectedCompany();
        List<Party> parties = selectedCompany.getParties();



        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item);
        for (Party party : parties) {
            adapter.add(party.getName().getName());
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partySpinner.setAdapter(adapter);

        //set current date to the due date input field
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = sdf.format(date);

        dueDate = (EditText)view.findViewById(R.id.dueDate_trans);
        dueDate.setText(dateString);
        dueDate.setOnFocusChangeListener(this);

        transDate = (EditText)view.findViewById(R.id.transDate_trans);
        transDate.setText(dateString);
        transDate.setOnFocusChangeListener(this);

        return view;
    }


    public void onFocusChange(final View v, boolean hasFocus){
        if(hasFocus){
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            EditText dateTextField = (EditText)getView().findViewById(
                                    v.getId());

                            dateTextField.setText((dayOfMonth + "-" + (monthOfYear + 1) + "-" + year));

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){

        }
    }
    private void submitTransactionData(){
        Transaction transaction = new Transaction();
        Company selectedCompany = Company.getSelectedCompany();

        Spinner partySpinner = (Spinner) getView().findViewById(R.id.partyListSpinner_Trans) ;
        Party selectedParty = Party.findParty(selectedCompany.getParties(), partySpinner.getSelectedItem().toString());

        transaction.setParty(selectedParty);

        transSwitch = (Switch)getView().findViewById(R.id.sell_buy_switch);
        transaction.setPurchase(transSwitch.isChecked());

        productInfo = (EditText)getView().findViewById(R.id.transProductInfo_Input);
        transaction.setProductInfo(productInfo.getText().toString());

        productQuandity = (EditText)getView().findViewById(R.id.transquandityInput);
        transaction.setQuantity(Float.parseFloat(productQuandity.getText().toString()));

        amtTransInput = (EditText)getView().findViewById(R.id.transAmtInput);
        transaction.setPrice(Float.parseFloat(amtTransInput.getText().toString()));
        transaction.setLeftOver(transaction.getPrice());

        Log.d("submitTransactionData: ",amtTransInput.getText().toString());

        description = (EditText)getView().findViewById(R.id.transDescInput);
        transaction.setDescription(description.getText().toString());

        transDate = (EditText)getView().findViewById(R.id.transDate_trans);
        SimpleDateFormat dfTrans = new SimpleDateFormat("dd-MM-yyyy");
        Date CurrentTransDate= new Date();
        try {
            CurrentTransDate = dfTrans.parse(transDate.getText().toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        transaction.setDueDate(CurrentTransDate);

        dueDate = (EditText)getView().findViewById(R.id.dueDate_trans);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date myDate= new Date();
        try {
            myDate = df.parse(transDate.getText().toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        transaction.setDueDate(myDate);

        TransactionIncluder transactionIncluder = new TransactionIncluder();
        transactionIncluder.addTransaction(transaction);
        Toast.makeText(getContext(),amtTransInput.getText().toString() + "-Transaction Successfully added",Toast.LENGTH_SHORT).show();

    }
}
