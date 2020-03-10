package com.example.bootcampassistant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bootcampassistant.data.model.BaseResponse;
import com.example.bootcampassistant.data.model.UserResponse;
import com.example.bootcampassistant.data.remote.ApiClient;
import com.example.bootcampassistant.data.remote.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    public static final String EXT_ID = "DetailActivity.Id";

    private Context context = this;
    private TextView txtName;
    private ImageView images;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;

    private int selectedUserId;

    public static void start(Context context, int userId) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXT_ID, userId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtName = findViewById(R.id.txt_name);
        images = findViewById(R.id.image);

        selectedUserId = getIntent().getIntExtra(EXT_ID, 0);
        getDataUser();
    }

    private void showProgress(boolean show) {
        if (show) {
            progressDialog = new ProgressDialog(DetailActivity.this);
            progressDialog.setMessage("Loading....");
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    private void getDataUser() {
        if (apiInterface == null) {
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
        }

        showProgress(true);

        Call<BaseResponse> call = apiInterface.getUser(selectedUserId);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                showProgress(false);
                BaseResponse baseResponse = response.body();
                UserResponse userResponse = null;
                if (baseResponse != null) {
                    userResponse = baseResponse.getUserResponse();
                }

                if (userResponse != null) {
                    txtName.setText(String.format("%s %s", userResponse.getFirstName(), userResponse.getLastName()));
                }

                if (userResponse != null) {
                    Glide.with(getBaseContext())
                            .load(userResponse.getAvatar())
                            .into(images);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                showProgress(false);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
