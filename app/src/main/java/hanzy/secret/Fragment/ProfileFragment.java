package hanzy.secret.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import hanzy.secret.R;
import hanzy.secret.aty.Aty_Test;

/**
 * Created by h on 2016/7/11.
 */
public class ProfileFragment extends Fragment{

    private Button btn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.profile_fragment,null);
        btn= (Button) view.findViewById(R.id.testbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), Aty_Test.class);
                startActivity(i);
            }
        });
        return view;
    }
}
