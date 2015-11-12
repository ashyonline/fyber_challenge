package codingbad.com.fyberchallenge.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import codingbad.com.fyberchallenge.R;
import codingbad.com.fyberchallenge.model.Offer;
import codingbad.com.fyberchallenge.ui.adapter.OfferAdapter;
import roboguice.inject.InjectView;

/**
 * Created by ayi on 11/12/15.
 */
public class FyberOffersFragment extends AbstractFragment<FyberOffersFragment.Callbacks> implements OfferAdapter.RecyclerViewListener {

    @InjectView(R.id.fragment_offers_recyclerview)
    private RecyclerView mOffersRecyclerView;
    private OfferAdapter mOfferAdapter;

    public FyberOffersFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_offers, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupOffers();
    }

    private void setupOffers() {
        mOffersRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());

        mOffersRecyclerView.setLayoutManager(layoutManager);

        mOfferAdapter = new OfferAdapter(this);
        mOfferAdapter.addItemList(callbacks.getOffers());
        mOffersRecyclerView.setAdapter(mOfferAdapter);
    }

    @Override
    public void onItemClickListener(View view, int position) {

    }

    public interface Callbacks {
        List<Offer> getOffers();
    }
}
