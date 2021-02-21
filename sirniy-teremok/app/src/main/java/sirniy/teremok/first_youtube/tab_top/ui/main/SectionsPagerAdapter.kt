package sirniy.teremok.first_youtube.tab_top.ui.main
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import sirniy.teremok.R
import sirniy.teremok.admin_activitys.utiles.App

// private val TAB_TITLES = arrayOf(
//    R.string.company, R.string.admin
// )

private val TAB_TITLES = App.GlobalListProductCategory

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    override fun getItem(position: Int): Fragment {
        return PlaceholderFragment.newInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence {
      //  return context.resources.getString(TAB_TITLES[position])
        return App.GlobalListProductCategory[position]
    }

    override fun getCount(): Int {
        return App.GlobalListProductCategory.size
    }
}