package com.photoeditor.blendphotoeditor.blendermixer.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.photoeditor.blendphotoeditor.blendermixer.R;

/*import android.support.v4.app.Fragment;*/
/*import com.naygrh.multiplephotoblendermixerphotoeditor.R;*/

public class NAGYRHAGA_StrickerFragment extends Fragment {
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.nagyrhaga_fragment_stricker, viewGroup, false);
        GridView gridView = (GridView) inflate.findViewById(R.id.StrickerGrid);
        return inflate;
    }
}
