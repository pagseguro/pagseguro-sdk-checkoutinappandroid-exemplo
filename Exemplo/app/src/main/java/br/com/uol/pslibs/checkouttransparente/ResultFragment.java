package br.com.uol.pslibs.checkouttransparente;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cooltechworks.creditcarddesign.CreditCardUtils;

import br.com.uol.pslibs.checkouttransparente.model.Product;


public class ResultFragment extends Fragment {

    private boolean isSuccess;
    private String message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_success, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("Pagamento");
        Button btBack = (Button) view.findViewById(R.id.bt_back);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        ImageView ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_message);
        if(isSuccess)
            ivIcon.setImageResource(R.drawable.ic_success);
        else
            ivIcon.setImageResource(R.drawable.ic_fail);
        tvMessage.setText(message);

        return view;
    }

    private void back() {
        getFragmentManager().popBackStack();
        getFragmentManager().popBackStack();
        getFragmentManager().popBackStack();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            message = bundle.getString(ResumeFragment.MESSAGE);
            isSuccess = bundle.getBoolean(ResumeFragment.IS_SUCCESS);
        }
    }
}
