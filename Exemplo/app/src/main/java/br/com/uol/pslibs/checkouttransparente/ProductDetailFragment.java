package br.com.uol.pslibs.checkouttransparente;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import br.com.uol.pslibs.checkouttransparente.model.Product;


public class ProductDetailFragment extends Fragment {

    final int GET_NEW_CARD = 2;
    public static final String PRODUCT = "PRODUCT";
    public static final String AMOUNT = "AMOUNT";
    public static final String TOTAL = "TOTAL";
    public static final String TOTAL_VALUE = "TOTAL_VALUE";

    private Product product;
    private double total;

    private TextView tvName;
    private TextView tvDescription;
    private TextView tvPrice;
    private TextView tvTotal;
    private TextView tvQtd;
    private ImageView ivProduct;
    private Button btBuy;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_product, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(product.getName());
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvDescription = (TextView) view.findViewById(R.id.tv_description);
        tvPrice = (TextView) view.findViewById(R.id.tv_price);
        tvQtd = (TextView) view.findViewById(R.id.tv_qtd);
        tvTotal = (TextView) view.findViewById(R.id.tv_total);
        ivProduct = (ImageView) view.findViewById(R.id.iv_product);
        btBuy = (Button) view.findViewById(R.id.bt_buy);

        tvName.setText(product.getName());
        tvDescription.setText(product.getDescription());
        tvPrice.setText(product.getPriceString());
        tvTotal.setText(product.getPriceString());
        ivProduct.setImageResource(product.getImage());
        total = product.getPrice();

        DiscreteSeekBar seekBar = (DiscreteSeekBar) view.findViewById(R.id.seek_bar);
        seekBar.setProgress(1);
        seekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                tvQtd.setText(value+" un");
                total = product.getPrice()*value;
                NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("pt","br"));
                DecimalFormat df = (DecimalFormat)numberFormat;
                ((DecimalFormat) numberFormat).applyPattern("###,###,###,###,##0.00");
                tvTotal.setText("R$"+df.format(total));
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {}
        });

        btBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CardEditActivity.class);
                startActivityForResult(intent,GET_NEW_CARD);
            }
        });
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == getActivity().RESULT_OK) {
            Bundle bundle = new Bundle();
            bundle.putString(CreditCardUtils.EXTRA_CARD_HOLDER_NAME, data.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME));
            bundle.putString(CreditCardUtils.EXTRA_CARD_NUMBER, data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER));
            bundle.putString(CreditCardUtils.EXTRA_CARD_EXPIRY, data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY));
            bundle.putString(CreditCardUtils.EXTRA_CARD_CVV, data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV));
            bundle.putSerializable(PRODUCT, product);
            bundle.putString(AMOUNT, tvQtd.getText().toString());
            bundle.putString(TOTAL, tvTotal.getText().toString());
            bundle.putDouble(TOTAL_VALUE, total);

            ResumeFragment resumeFragment = new ResumeFragment();
            resumeFragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_id, resumeFragment).addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

}
