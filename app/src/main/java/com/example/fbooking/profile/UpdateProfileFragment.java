package com.example.fbooking.profile;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fbooking.R;
import com.example.fbooking.userloginandsignup.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class UpdateProfileFragment extends Fragment {
    private View view;
    private EditText edtUpdateName, edtUpdateDate, edtUpdatePhone, edtUpdateIdPerson, edtEmailUpdate;
    private AppCompatButton btnUpdateProfile, btnCancel;
    private TextView tvShowErrorUpdateProfile;
    private ProgressDialog progressDialog;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_update_profile, container, false);

        initUi();

        showUserInformation();

        onClickButton();

        return view;
    }

    //Chon ngay sinh
    private void choseDateOfBirth() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateCalender();
            }

            private void updateCalender() {
                String formatDate = "dd/MM/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.CHINA);
                edtUpdateDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        edtUpdateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    //Hien thi thong tin nguoi dung
    public void showUserInformation() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String fullname = userProfile.name;
                    String dateOfBirth = userProfile.dateOfBirth;
                    String phoneNumber = userProfile.phoneNumber;
                    String idPerson = userProfile.idPerson;
                    String email = userProfile.email;

                    edtUpdateName.setText(fullname);
                    edtUpdateDate.setText(dateOfBirth);
                    edtUpdatePhone.setText(phoneNumber);
                    edtUpdateIdPerson.setText(idPerson);
                    edtEmailUpdate.setText(email);
                }

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickButton() {
        //Chon ngay sinh
        choseDateOfBirth();

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtUpdateName.getText().toString().trim();
                String dateOfBirth = edtUpdateDate.getText().toString().trim();
                String phoneNumber = edtUpdatePhone.getText().toString().trim();
                String idPerson = edtUpdateIdPerson.getText().toString().trim();
                String email = edtEmailUpdate.getText().toString().trim();

                if (name.isEmpty() && dateOfBirth.isEmpty() && phoneNumber.isEmpty() && idPerson.isEmpty()) {
                    tvShowErrorUpdateProfile.setText("Không được bỏ trống thông tin!");
                    return;
                }

                if (name.isEmpty()) {
                    tvShowErrorUpdateProfile.setText("Bạn chưa chọn nhập họ và tên!");
                    return;
                }

                if (dateOfBirth.isEmpty()) {
                    tvShowErrorUpdateProfile.setText("Bạn chưa chọn nhập ngày sinh!");
                    return;
                }

                if (phoneNumber.isEmpty()) {
                    tvShowErrorUpdateProfile.setText("Bạn chưa chọn nhập số điện thoại!");
                    return;
                } else if (!phoneNumber.matches(String.valueOf(Patterns.PHONE))) {
                    tvShowErrorUpdateProfile.setText("Sai định dạng số điện thoại!");
                    return;
                }

                if (idPerson.isEmpty()) {
                    tvShowErrorUpdateProfile.setText("Bạn chưa chọn nhập CMND/CCCD!");
                    return;
                }

                tvShowErrorUpdateProfile.setVisibility(View.GONE);
                updateUser(name, dateOfBirth, phoneNumber, idPerson, email);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.GONE);
            }
        });
    }

    //Cap nhat thong tin nguoi dung
    private void updateUser(String name, String dateOfBirth, String phoneNumber, String idPerson, String email) {
        HashMap userUpdate = new HashMap();
        userUpdate.put("name", name);
        userUpdate.put("dateOfBirth", dateOfBirth);
        userUpdate.put("phoneNumber", phoneNumber);
        userUpdate.put("idPerson", idPerson);
        userUpdate.put("email", email);

        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();

        progressDialog.show();
        reference.child(userId).updateChildren(userUpdate).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                    view.setVisibility(View.GONE);
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initUi() {
        edtUpdateName = view.findViewById(R.id.edt_update_name_profile);
        edtUpdateDate = view.findViewById(R.id.edt_update_date_profile);
        edtUpdatePhone = view.findViewById(R.id.edt_phone_update_profile);
        edtUpdateIdPerson = view.findViewById(R.id.edt_update_id_person_profile);
        edtEmailUpdate = view.findViewById(R.id.edt_email_update_profile);

        tvShowErrorUpdateProfile = view.findViewById(R.id.tv_show_error_update_profile);

        btnUpdateProfile = view.findViewById(R.id.btn_update_profile);
        btnCancel = view.findViewById(R.id.btn_cancel_update_profile);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Đang kiểm tra thông tin...");
    }
}