package br.com.uol.pslibs.checkouttransparente.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import br.com.uol.pslibs.checkouttransparente.MainActivity;
import br.com.uol.pslibs.checkouttransparente.R;
import br.com.uol.pslibs.checkouttransparente.ProductDetailFragment;
import br.com.uol.pslibs.checkouttransparente.model.Product;


public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder>{

    private List<Product> products;
    private Context context;

    public ProductListAdapter (List<Product> products, Context context){
        this.context = context;
        this.products = products;
    }

    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Product product = products.get(position);
        holder.tvName.setText(product.getName());
        holder.tvDescription.setText(product.getDescription());
        holder.tvPrice.setText(product.getPriceString());
        holder.ivPhoto.setImageResource(product.getImage());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductDetailFragment productDetailFragment = new ProductDetailFragment();
                productDetailFragment.setProduct(product);
                FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_id, productDetailFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvDescription;
        TextView tvPrice;
        ImageView ivPhoto;
        CardView cardView;

        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvDescription = (TextView) view.findViewById(R.id.tv_description);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
            ivPhoto = (ImageView) view.findViewById(R.id.iv_photo);
            cardView = (CardView) view.findViewById(R.id.card_item);
        }
    }
}
