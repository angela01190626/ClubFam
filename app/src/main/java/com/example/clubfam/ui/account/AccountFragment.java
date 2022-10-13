package com.example.clubfam.ui.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProviders;

import com.example.clubfam.R;

@SuppressWarnings("FieldCanBeLocal")
public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;
    private static final String TEMP_INFO="temp_info";

    TextView textViewName;
    TextView textViewAge;
    EditText editTextAge;
    TextView textViewMajor;
    EditText editTextMajor;
    TextView textViewMyInterest;
    EditText editTextMyInterest;
    TextView textViewEmail;
    EditText editTextEmail;
    TextView textViewPhoneNumber;
    EditText editTextPhoneNumber;
    ImageView imageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel =
                ViewModelProviders.of(this).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        textViewName = (TextView) root.findViewById(R.id.textView1);
        textViewAge = (TextView) root.findViewById(R.id.textView2);
        editTextAge =  (EditText) root.findViewById(R.id.editText2);
        textViewMajor = (TextView) root.findViewById(R.id.textView3);
        editTextMajor =  (EditText) root.findViewById(R.id.editText3);
        textViewMyInterest = (TextView) root.findViewById(R.id.textView4);
        editTextMyInterest =  (EditText) root.findViewById(R.id.editText4);
        textViewEmail = (TextView) root.findViewById(R.id.textView5);
        editTextEmail =  (EditText) root.findViewById(R.id.editText5);
        textViewPhoneNumber = (TextView) root.findViewById(R.id.textView6);
        editTextPhoneNumber =  (EditText) root.findViewById(R.id.editText6);
        imageView = (ImageView) root.findViewById(R.id.imageView2);

        SharedPreferences sp = getActivity().getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE);
        String age = sp.getString("user_age", "");
        String major = sp.getString("user_major", "");
        String interests = sp.getString("user_interests", "");
        String email = sp.getString("user_email", "");
        String phoneNumber = sp.getString("user_phoneNumber", "");
        String name = sp.getString("user_name", "");
        textViewName.setText(name);
        editTextAge.setText(age);
        editTextMajor.setText(major);
        editTextMyInterest.setText(interests);
        editTextEmail.setText(email);
        editTextPhoneNumber.setText(phoneNumber);

        return root;
    }

    public void onStop(){
        super.onStop();
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(TEMP_INFO, Context.MODE_PRIVATE).edit();
        editor.putString("user_age", editTextAge.getText().toString());
        editor.putString("user_major", editTextMajor.getText().toString());
        editor.putString("user_interests", editTextMyInterest.getText().toString());
        editor.putString("user_email", editTextEmail.getText().toString());
        editor.putString("user_phoneNumber", editTextPhoneNumber.getText().toString());
        editor.apply();
        editor.commit();
    }
}

