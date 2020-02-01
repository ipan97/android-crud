package com.github.ipan97.kpexpress.ui.fragment;

import androidx.fragment.app.Fragment;

import com.github.ipan97.kpexpress.ui.activity.MainActivity;

/**
 * @author Ipan Taupik Rahman.
 */
public class BaseFragment extends Fragment {
    protected MainActivity getBaseActivity() {
        return ((MainActivity) getActivity());
    }
}
