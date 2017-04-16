package teach.duy.com.texttool.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by DUy on 06-Feb-17.
 */

public class PagerSectionAdapter extends FragmentPagerAdapter {
    private static final int COUNT = 9;

    public PagerSectionAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TextFragment.newInstance(TextFragment.TextType.ASCII);
            case 1:
                return TextFragment.newInstance(TextFragment.TextType.BINARY);
            case 2:
                return TextFragment.newInstance(TextFragment.TextType.HEX);
            case 3:
                return TextFragment.newInstance(TextFragment.TextType.OCTAL);
            case 4:
                return TextFragment.newInstance(TextFragment.TextType.REVERSER);
            case 5:
                return TextFragment.newInstance(TextFragment.TextType.UPPER);
            case 6:
                return TextFragment.newInstance(TextFragment.TextType.LOWER);
            case 7:
                return TextFragment.newInstance(TextFragment.TextType.UPSIDE_DOWNSIDE);
            case 8:
                return StyleListFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 8:
                return "Style";
            case 0:
                return "Ascii";
            case 1:
                return "Binary";
            case 2:
                return "Hex";
            case 3:
                return "Octal";
            case 4:
                return "Reverser";
            case 5:
                return "Upper";
            case 6:
                return "Lower";
            case 7:
                return "Upside down";
            default:
                return "";
        }
    }

}
