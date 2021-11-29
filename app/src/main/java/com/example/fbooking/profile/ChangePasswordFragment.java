package com.example.fbooking.profile;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends Fragment {
    private EditText edtChangePassword, edtConfirmChangePassword;
    private AppCompatButton btnChangePassword, btnCancel;
    private View view;
    private ImageView btnHideShowPassword, btnHideShowConfirmPassword;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_change_password, container, false);
        
        initUi();

        onClickButton();

        return view;
    }

    //Hien mat khau
    private void showHidePass() {
        if (edtChangePassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            //Show Password
            edtChangePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //Hide Password
            edtChangePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    //Hien xac nhan mat khau
    private void showHideConfirmPass() {
        if (edtConfirmChangePassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            //Show Password
            edtConfirmChangePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //Hide Password
            edtConfirmChangePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    private void onClickButton() {
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNewPassword = edtChangePassword.getText().toString().trim();
                String strConfirmPassword = edtConfirmChangePassword.getText().toString().trim();

                if (strNewPassword.isEmpty() && strConfirmPassword.isEmpty()) {
                    edtChangePassword.setError("Không được để trống!");
                    edtChangePassword.requestFocus();

                    edtConfirmChangePassword.setError("Không được để trống!");
                    edtConfirmChangePassword.requestFocus();
                    return;
                }

                if (strNewPassword.length() < 8) {
                    edtChangePassword.setError("Mật khẩu ít nhất có 8 ký tự!");
                    edtChangePassword.requestFocus();
                    return;
                }

                if (strConfirmPassword.length() != strNewPassword.length()) {
                    edtConfirmChangePassword.setError("Sai mật khẩu!");
                    edtConfirmChangePassword.requestFocus();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Bạn chắc chắn muốn đổi mật khẩu?")
                        .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.updatePassword(strNewPassword)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getActivity(), "Thay đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                getActivity().onBackPressed();
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

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.GONE);
            }
        });

        btnHideShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHidePass();
            }
        });

        btnHideShowConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHideConfirmPass();
            }
        });
    }

    private void initUi() {
        edtChangePassword = view.findViewById(R.id.edt_change_password);
        edtConfirmChangePassword = view.findViewById(R.id.edt_confirm_change_password);
        btnHideShowPassword = view.findViewById(R.id.btn_hide_show_change_password);
        btnHideShowConfirmPassword = view.findViewById(R.id.btn_hide_show_confirm_change_password);
        btnChangePassword = view.findViewById(R.id.btn_change_password);
        btnCancel = view.findViewById(R.id.btn_cancel_change_password);
    }
}