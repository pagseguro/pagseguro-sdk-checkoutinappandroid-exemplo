package br.com.uol.pslibs.checkouttransparente;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.creditcarddesign.CreditCardUtils;
import br.com.uol.pslibs.checkouttransparent.PSCheckout;
import br.com.uol.pslibs.checkouttransparent.vo.PSCheckoutRequest;
import br.com.uol.pslibs.checkouttransparent.vo.PSCheckoutResponse;
import br.com.uol.pslibs.checkouttransparent.vo.SellerVO;
import br.com.uol.pslibs.checkouttransparente.model.Product;

public class ResumeFragment extends Fragment {

    //Configuração Seller
    /**
     * Seller Email
     */
    private static final String SELLER_EMAIL = "<e-mail do vendedor>";

    /**
     * Seller Token
     * Este token deve ser obtido no ibanking do PagSeguro
     * www.pagseguro.com.br
     * -> Mais informações consulte a documentação.
     */
    private static final String SELLER_TOKEN = "<token do vendedor>";


    public static final String MESSAGE = "MESSAGE";
    public static final String IS_SUCCESS = "IS_SUCCESS";

    private ProgressDialog progress;

    private String cardName;
    private String cardNumber;
    private String cardExpiry;
    private String cardCvv;

    private Product product;
    private String amount;
    private String total;
    private Double totalValue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            cardName = bundle.getString(CreditCardUtils.EXTRA_CARD_HOLDER_NAME);
            cardNumber = bundle.getString(CreditCardUtils.EXTRA_CARD_NUMBER);
            cardExpiry = bundle.getString(CreditCardUtils.EXTRA_CARD_EXPIRY);
            cardCvv = bundle.getString(CreditCardUtils.EXTRA_CARD_CVV);
            product = (Product) bundle.getSerializable(ProductDetailFragment.PRODUCT);
            total = bundle.getString(ProductDetailFragment.TOTAL);
            amount = bundle.getString(ProductDetailFragment.AMOUNT);
            totalValue = bundle.getDouble(ProductDetailFragment.TOTAL_VALUE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resume, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        progress = new ProgressDialog(getActivity());

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("Resumo do Pedido");

        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button btConfirm = (Button)view.findViewById(R.id.bt_confirm);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configLib();
                pay(cardNumber, cardExpiry, cardCvv);
            }
        });

        ImageView ivProduct = (ImageView) view.findViewById(R.id.iv_photo);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvDescription = (TextView) view.findViewById(R.id.tv_description);
        TextView tvAmount = (TextView) view.findViewById(R.id.tv_amount);
        TextView tvPrice = (TextView) view.findViewById(R.id.tv_price);
        TextView tvTotal = (TextView) view.findViewById(R.id.tv_total);
        TextView tvCardName = (TextView) view.findViewById(R.id.tv_card_name);
        TextView tvCardNumber = (TextView) view.findViewById(R.id.tv_card_number);
        TextView tvCardValidity = (TextView) view.findViewById(R.id.tv_card_validity);

        ivProduct.setImageResource(product.getImage());
        tvName.setText(product.getName());
        tvDescription.setText(product.getDescription());
        tvAmount.setText(amount);
        tvPrice.setText(product.getPriceString());
        tvTotal.setText(total);
        tvCardName.setText(cardName);
        tvCardNumber.setText(formatCardNumber(cardNumber));
        tvCardValidity.setText(cardExpiry);
    }

    private void configLib() {
        SellerVO sellerVO = new SellerVO(SELLER_EMAIL, SELLER_TOKEN);
        PSCheckout.init(getActivity(), sellerVO);
    }

    private void pay(String number, String expiry, String cvv) {
        PSCheckoutRequest psCheckoutRequest = new PSCheckoutRequest();

        //NUMERO DO CARTAO
        psCheckoutRequest.setCreditCard(number);
        //CVV DO CARTAO
        psCheckoutRequest.setCvv(cvv);
        //MÊS DE EXPIRACAO (Ex: 03)
        psCheckoutRequest.setExpMonth(expiry.substring(0,2));
        //ANO DE EXPIRACAO, ULTIMOS 2 DIGITOS (Ex: 17)
        psCheckoutRequest.setExpYear(expiry.substring(3,5));
        //VALOR DA TRANSACAO
        psCheckoutRequest.setAmountPayment(totalValue);
        //DESCRICAO DO PRODUTO/SERVICO
        psCheckoutRequest.setDescriptionPayment(product.getName());

        PSCheckout.pay(psCheckoutRequest, psCheckoutListener, (AppCompatActivity) getActivity());
    }

    private PSCheckout.PSCheckoutListener psCheckoutListener = new PSCheckout.PSCheckoutListener() {
        @Override
        public void onSuccess(PSCheckoutResponse responseVO) {
            progress.dismiss();
            callResult(true, responseVO.getMessage());
        }

        @Override
        public void onFailure(PSCheckoutResponse responseVO) {
            progress.dismiss();
            callResult(false, responseVO.getMessage());
        }

        @Override
        public void onProcessing() {
            progress.setMessage("Realizando pagamento...");
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.show();
        }
    };

    private void callResult(boolean isSuccess, String message){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ResultFragment resultFragment = new ResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ResumeFragment.MESSAGE, message);
        bundle.putBoolean(ResumeFragment.IS_SUCCESS, isSuccess);
        resultFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.container_id, resultFragment).addToBackStack(null).commit();
    }

    private String formatCardNumber(String cardNumber) {
        if(cardNumber.startsWith("3"))
            return "****  ******  "+cardNumber.substring(10);
        else
            return "****  ****  ****  "+cardNumber.substring(12);
    }
}
