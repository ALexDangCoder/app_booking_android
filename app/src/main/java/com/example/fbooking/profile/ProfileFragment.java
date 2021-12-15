package com.example.fbooking.profile;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.fbooking.R;
import com.example.fbooking.booking.PayActivity;
import com.example.fbooking.userloginandsignup.LoginActivity;
import com.example.fbooking.userloginandsignup.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private View view;
    private ImageView imgAvatarProfile, btnSignOut;
    private TextView tvNameProfile, tvDateOfBirthProfile, tvPhoneNumberProfile,
            tvIdPersonProfile, tvEmailProfile, tvPasswordProfile, tvOpenChangePassword;
    private TextView tvOpenUse;
    private AppCompatButton btnOpenUpdateProfile, btnOpenPay;

    private FirebaseUser user;
    private DatabaseReference reference;

    private ProgressDialog progressDialog;
    private SwipeRefreshLayout srlProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_profile, container, false);

        initUi();

        //Hien thi thong tin nguoi dung
        showUserInformation();

        srlProfile.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                progressDialog.show();
                showUserInformation();
            }
        });
        onOpenUpdateInfo();

        onOpenChangePassword();

        openPay();

        openHowToUse();

        onClickSignOut();

        return view;
    }

    private void openHowToUse() {
        tvOpenUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HowToUseActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openPay() {
        btnOpenPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PayActivity.class);
                startActivity(intent);
            }
        });
    }

    //Hien thi thong tin nguoi dung
    public void showUserInformation() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        if (user == null) {
            return;
        }

        Glide.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.ic_profile).into(imgAvatarProfile);

        reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String fullname = userProfile.name;
                    String dateOfBirth = userProfile.dateOfBirth;
                    String phoneNumber = userProfile.phoneNumber;
                    String idPerson = userProfile.idPerson;
                    String email = userProfile.email;

                    tvNameProfile.setText("Xin chào, " + fullname);
                    tvDateOfBirthProfile.setText(dateOfBirth);
                    tvPhoneNumberProfile.setText(phoneNumber);
                    tvIdPersonProfile.setText(idPerson);
                    tvEmailProfile.setText(email);
                    tvPasswordProfile.setText("********");

                    srlProfile.setRefreshing(false);
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                } else {
                    tvNameProfile.setText("Xin chào,");
                    tvDateOfBirthProfile.setText("Chưa cập nhật");
                    tvPhoneNumberProfile.setText("Chưa cập nhật");
                    tvIdPersonProfile.setText("Chưa cập nhật");
                    tvEmailProfile.setText("Chưa cập nhật");
                    tvPasswordProfile.setText("Chưa cập nhật");
                    btnOpenUpdateProfile.setText("Đăng nhập để tiếp tục");
                    srlProfile.setRefreshing(false);
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Mo man hinh cap nhat thong tin
    private void onOpenUpdateInfo() {
        if (user == null) {
            btnOpenUpdateProfile.setText("Đăng nhập");
            btnOpenUpdateProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            });
        } else {
            btnOpenUpdateProfile.setEnabled(true);
            btnOpenUpdateProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    UpdateProfileFragment updateProfileFragment = new UpdateProfileFragment();
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fl_profile, updateProfileFragment).addToBackStack(null).commit();
                }
            });
        }
    }

    //Mo man hinh doi mat khau
    private void onOpenChangePassword() {
        if (user == null) {
            tvOpenChangePassword.setVisibility(View.GONE);
            tvOpenChangePassword.setEnabled(false);
        } else {
            tvOpenChangePassword.setVisibility(View.VISIBLE);
            tvOpenChangePassword.setEnabled(true);
            tvOpenChangePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fl_profile, changePasswordFragment).addToBackStack(null).commit();
                }
            });
        }
    }

    //Dang xuat
    public void onClickSignOut() {
        if (user == null) {
            btnSignOut.setVisibility(View.GONE);
            btnOpenPay.setVisibility(View.GONE);
            btnSignOut.setEnabled(false);
        } else {
            btnSignOut.setVisibility(View.VISIBLE);
            btnOpenPay.setVisibility(View.VISIBLE);
            btnSignOut.setEnabled(true);
            btnSignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Đăng xuất tài khoản?")
                            .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseAuth.getInstance().signOut();
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.show();
                }
            });
        }
    }

    //Ham anh xa
    private void initUi() {
        imgAvatarProfile = (ImageView) view.findViewById(R.id.img_avatar_profile);

        tvNameProfile = view.findViewById(R.id.tv_greeting_profile);
        tvDateOfBirthProfile = view.findViewById(R.id.tv_date_of_birth_profile);
        tvPhoneNumberProfile = view.findViewById(R.id.tv_phone_number_profile);
        tvIdPersonProfile = view.findViewById(R.id.tv_id_person_profile);
        tvEmailProfile = view.findViewById(R.id.tv_email_profile);
        tvPasswordProfile = view.findViewById(R.id.tv_password_profile);

        tvOpenUse = view.findViewById(R.id.tv_open_use);

        tvOpenChangePassword = view.findViewById(R.id.tv_open_change_password);
        tvOpenChangePassword.setPaintFlags(tvOpenChangePassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnOpenUpdateProfile = view.findViewById(R.id.btn_open_update_profile);
        btnOpenPay = view.findViewById(R.id.btn_open_pay_profile);
        btnSignOut = view.findViewById(R.id.btn_sign_out);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Đang kiểm tra thông tin...");
        srlProfile = view.findViewById(R.id.srl_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
    }
}