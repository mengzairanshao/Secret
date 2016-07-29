package hanzy.secret.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import hanzy.secret.R;
import hanzy.secret.aty.AtyLogin;
import hanzy.secret.net.Logout;
import hanzy.secret.secret.Config;
import hanzy.secret.secret.StartingActivity;


/**
 * Created by h on 2016/7/11.
 */
public class ProfileFragment extends Fragment{

    private String TAG="ProfileFragment";
    private Button btnLogout;
    private Button btnLogin;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.profile_fragment,null);
        btnLogin= (Button) view.findViewById(R.id.btnLogin);
        btnLogout= (Button) view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Config.getCachedDATA(getActivity(),Config.IS_LOGINED).equals("Login_succeed")){
                    new Logout(getActivity(), new Logout.SuccessCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Toast.makeText(getActivity(), R.string.Logout_succeed,Toast.LENGTH_LONG).show();
                        }
                    }, new Logout.FailCallback() {
                        @Override
                        public void onFail() {

                        }
                    });
                }else {
                    Toast.makeText(getActivity(), R.string.you_has_not_login,Toast.LENGTH_LONG).show();
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Config.getCachedDATA(getActivity(),Config.IS_LOGINED).equals("Login_succeed")){
                    startActivity(new Intent(getActivity(), AtyLogin.class));
                }else {
                    Toast.makeText(getActivity(), R.string.you_have_logined,Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}
