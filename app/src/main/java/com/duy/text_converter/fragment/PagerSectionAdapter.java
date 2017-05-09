package teach.duy.com.texttool.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by DUy on 06-Feb-17.
 */

public class PagerSectionAdapter extends FragmentPagerAdapter {
    private static final int COUNT = 11;
    private  String init;

    public PagerSectionAdapter(FragmentManager fm) {
        super(fm);
    }

    public PagerSectionAdapter(FragmentManager fm, String init) {
        super(fm);
        this.init = init;
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TextFragment.newInstance(TextFragment.TextType.ASCII, init);
            case 1:
                return TextFragment.newInstance(TextFragment.TextType.BINARY, init);
            case 2:
                return TextFragment.newInstance(TextFragment.TextType.HEX, init);
            case 3:
                return TextFragment.newInstance(TextFragment.TextType.OCTAL, init);
            case 4:
                return TextFragment.newInstance(TextFragment.TextType.REVERSER, init);
            case 5:
                return TextFragment.newInstance(TextFragment.TextType.UPPER, init);
            case 6:
                return TextFragment.newInstance(TextFragment.TextType.LOWER, init);
            case 7:
                return TextFragment.newInstance(TextFragment.TextType.UPSIDE_DOWNSIDE, init);
            case 8:
                return TextFragment.newInstance(TextFragment.TextType.SUPPER_SCRIPT, init);
            case 9:
                return TextFragment.newInstance(TextFragment.TextType.SUB_SCRIPT, init);
            case 10:
                return StyleListFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {

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
            case 8:
                return "Supper script";
            case 9:
                return "Sub script";
            case 10:
                return "Style";
            default:
                return "";
        }
    }

}
