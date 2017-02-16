package br.com.uol.pslibs.checkouttransparente;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import br.com.uol.pslibs.checkouttransparente.adapter.ProductListAdapter;
import br.com.uol.pslibs.checkouttransparente.model.Product;

public class ListProductFragment extends Fragment {

    private List<Product> products;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_product, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        builArray();

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("Loja Modelo");

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_products);
        ProductListAdapter adapter = new ProductListAdapter(products, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void builArray() {
        products = new ArrayList<>();

        Product p1 = new Product();
        p1.setName("Notebook Prata");
        p1.setDescription("Intel® Core™ i7 Quad Core 14.0\" FullHD+ 16:9 Ratio 2.2GHZ - 8GB - 128GB SATA III SSD");
        p1.setPrice(1.00);
        p1.setPriceString("R$1,00");
        p1.setImage(R.drawable.product1);

        Product p2 = new Product();
        p2.setName("Notebook Rosa");
        p2.setDescription("Intel® Core™ i7 Quad Core 14.0\" FullHD+ 16:9 Ratio 2.2GHZ - 16GB - 256GB SATA III SSD");
        p2.setPrice(3.20);
        p2.setPriceString("R$3,20");
        p2.setImage(R.drawable.product2);

        Product p3 = new Product();
        p3.setName("Notebook Preto");
        p3.setDescription("Intel® Core™ i7 Quad Core 14.0\" FullHD+ 16:9 Ratio 2.2GHZ - 32GB - 512GB SATA III SSD");
        p3.setPrice(4.10);
        p3.setPriceString("R$4,10");
        p3.setImage(R.drawable.product3);

        Product p4 = new Product();
        p4.setName("Notebook Prata");
        p4.setDescription("Intel® Core™ i7 Quad Core 14.0\" FullHD+ 16:9 Ratio 2.2GHZ - 32GB - 512GB SATA III SSD");
        p4.setPrice(5.80);
        p4.setPriceString("R$5,80");
        p4.setImage(R.drawable.product1);

        products.add(p1);
        products.add(p2);
        products.add(p3);
        products.add(p4);
    }

}
