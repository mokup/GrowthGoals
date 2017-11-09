package marashoft.growthgoals.fragments.monthly;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import marashoft.growthgoals.R;
import marashoft.growthgoals.fragments.AbstractFragment;

/**
 * Created by Alessandro on 21/10/2017.
 */

public class MonthlyFragment extends AbstractFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.daily_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
