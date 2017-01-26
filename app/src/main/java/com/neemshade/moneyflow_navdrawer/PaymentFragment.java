package com.neemshade.moneyflow_navdrawer;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.neemshade.moneyflow_navdrawer.data.Payment;
import com.neemshade.moneyflow_navdrawer.services.PaymentIncluder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment implements View.OnFocusChangeListener {

    EditText txtDate,paidAmount;
    private int mYear, mMonth, mDay;
    Button submitBtn;
    Switch paidSwitch;
    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_payment, container, false);

        txtDate=(EditText)view.findViewById(R.id.dueDate_payment);

        submitBtn = (Button)view.findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paidAmount = (EditText) getView().findViewById(R.id.amountPaidInput);
                if(paidAmount.getText().toString().trim().equals("")) {

                    paidAmount.setError("Price is Required");
                }
                else
                submitPaymentData();
            }
        });

        paidSwitch = (Switch)view.findViewById(R.id.paidSwitch);
        paidSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

            }
        });

        final Spinner partySpinner = (Spinner) view.findViewById(R.id.partyListSpinner_payment) ;

        Company selectedCompany = Company.getSelectedCompany();
        List<Party> parties = selectedCompany.getParties();


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item);
        for (Party party : parties) {
            adapter.add(party.getName().getName());
        }
        //set current date to the due date input field
        long date = System.currentTimeMillis();

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partySpinner.setAdapter(adapter);

        txtDate = (EditText) view.findViewById(R.id.dueDate_payment);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = sdf.format(date);
        txtDate.setText(dateString);

        txtDate.setOnFocusChangeListener(this);

        return view;
    }
    public void onFocusChange(View v, boolean hasFocus){
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

                             txtDate.setText((dayOfMonth + "-" + (monthOfYear + 1) + "-" + year));

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

    private void submitPaymentData() {

        Payment payment = new Payment();

        Company selectedCompany = Company.getSelectedCompany();

        Spinner partySpinner = (Spinner) getView().findViewById(R.id.partyListSpinner_payment) ;
        Party selectedParty = Party.findParty(selectedCompany.getParties(), partySpinner.getSelectedItem().toString());

        payment.setParty(selectedParty);

        paidSwitch = (Switch)getView().findViewById(R.id.paidSwitch);
        payment.setPaid(paidSwitch.isChecked());

        paidAmount = (EditText)getView().findViewById(R.id.amountPaidInput);
        payment.setAmount(Float.parseFloat(paidAmount.getText().toString()));
        payment.setLeftOver(payment.getAmount());



        txtDate = (EditText)getView().findViewById(R.id.dueDate_payment);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date myDate= new Date();
        try {
            myDate = df.parse(txtDate.getText().toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        payment.setPaymentDate(myDate);


        PaymentIncluder paymentIncluder = new PaymentIncluder();
        paymentIncluder.addPayment(payment);
        Toast.makeText(getContext(),paidAmount.getText().toString() + "-Payment Successfully added",Toast.LENGTH_SHORT).show();

    }

}
