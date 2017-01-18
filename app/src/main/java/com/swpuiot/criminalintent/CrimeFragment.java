package com.swpuiot.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by DELL on 2016/12/8.
 */
public class CrimeFragment extends Fragment {
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_CONTACT = 1;
    private Crime mCrime;
    private EditText editText;
    private Button button;
    private CheckBox mSolvedCheckBox;
    private Button mReportButton;
    private Button mSuspectButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.getsCrimeLab(getActivity()).getCrime(crimeId);
    }

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取View
        View view = inflater.inflate(R.layout.frament_crime, container, false);
        //找到ID
        editText = (EditText) view.findViewById(R.id.crime_title);
        editText.setText(mCrime.getmTitle());
        button = (Button) view.findViewById(R.id.crime_date);
        mReportButton = (Button) view.findViewById(R.id.crime_report);
        updateDate();
        mSolvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved);
        final Intent pickContact = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);

        mSuspectButton = (Button) view.findViewById(R.id.crime_suspect);
        mSuspectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(pickContact, REQUEST_CONTACT);
            }
        });
        if (mCrime.getmSuspect() != null) {
            mSuspectButton.setText(mCrime.getmSuspect());
        }
//        //添加逻辑

        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity(pickContact,
                packageManager.MATCH_DEFAULT_ONLY)==null){
            mSuspectButton.setEnabled(false);
        }

        mReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
                i.putExtra(Intent.EXTRA_SUBJECT,
                        getString(R.string.crime_report_subject));
                i = Intent.createChooser(i, getString(R.string.send_report));
                //创建选择器
                startActivity(i);
            }
        });

        mSolvedCheckBox.setChecked(mCrime.ismSolved());
        updateDate();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getmDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fragmentManager, DIALOG_DATE);
            }
        });
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCrime.setmSolved(b);
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCrime.setmTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            Log.e("CrimeFragment", "failed");
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setmDate(date);
            updateDate();
        }
        else if (requestCode == REQUEST_CONTACT && data!=null){
            Uri contactUri = data.getData();
            String[] queryFields = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME
            };
            Cursor c = getActivity().getContentResolver()
                    .query(contactUri,queryFields,null,null,null);

            try{
                if (c.getCount() ==0)
                    return;
                c.moveToFirst();
                String suspect = c.getString(0);
                mCrime.setmSuspect(suspect);
                mSuspectButton.setText(suspect);
            }finally {
                c.close();
            }

        }
    }

    private void updateDate() {
        button.setText(mCrime.getmDate().toString());
    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.getsCrimeLab(getActivity()).
                updateCrime(mCrime);
    }

    private String getCrimeReport() {
        String solvedString = null;
        if (mCrime.ismSolved()) {
            solvedString = getString(R.string.crime_report_solved);

        } else {
            solvedString = getString(R.string.crime_report_unsolved);
        }
        String dateFormat = "EEE,MMM dd";
        String dateString = DateFormat.format(dateFormat, mCrime.getmDate()).toString();

        String suspect = mCrime.getmSuspect();
        if (suspect == null) {
            suspect = getString(R.string.crime_report_no_suspect);
        } else {
            suspect = getString(R.string.crime_report_suspect,suspect);
        }
        String report = getString(R.string.crime_report, mCrime.getmTitle(), dateString, solvedString, suspect);

        return report;
    }
}
