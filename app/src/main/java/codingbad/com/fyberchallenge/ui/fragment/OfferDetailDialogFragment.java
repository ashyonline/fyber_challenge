package codingbad.com.fyberchallenge.ui.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

// import com.bumptech.glide.Glide;

import com.squareup.picasso.Picasso;

import codingbad.com.fyberchallenge.R;
import codingbad.com.fyberchallenge.model.Offer;
import roboguice.fragment.RoboDialogFragment;
import roboguice.inject.InjectView;

/**
 * Created by ayi on 11/12/15.
 */
public class OfferDetailDialogFragment extends RoboDialogFragment {

    @InjectView(R.id.fragment_offer_details_icon)
    private ImageView mIcon;

    @InjectView(R.id.fragment_offer_details_title_value)
    private TextView mTitle;

    @InjectView(R.id.fragment_offer_details_teaser_value)
    private TextView mTeaser;

    @InjectView(R.id.fragment_offer_details_payout_value)
    private TextView mPayout;

    public static final String OFFER = "offer";
    private Offer mOffer;

    public OfferDetailDialogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offer_details, container);
        mOffer = (Offer) getArguments().getSerializable(OFFER);
        return view;
    }

    private void setupView() {
        mTitle.setText(mOffer.getTitle());
        mTeaser.setText(mOffer.getTeaser());
        mPayout.setText(mOffer.getPayout());
        String url = mOffer.getHiresThumbnailUrl();

        int size = getResources().getDimensionPixelSize(R.dimen.icon_size);
        Picasso.with(getContext())
                .load(url)
                .centerCrop()
                .resize(size, size)
                .placeholder(R.drawable.ic_send)
                .into(mIcon);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
    }
}
