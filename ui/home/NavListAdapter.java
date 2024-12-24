package tamhoang.ldpro4.ui.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import tamhoang.ldpro4.R;
import tamhoang.ldpro4.ui.home.NavItem;

import java.util.List;

/* loaded from: classes.dex */
public class NavListAdapter extends ArrayAdapter<tamhoang.ldpro4.ui.home.NavItem> {
    Context context;
    List<tamhoang.ldpro4.ui.home.NavItem> listNavItems;
    int resLayout;

    public NavListAdapter(Context context2, int resLayout2, List<tamhoang.ldpro4.ui.home.NavItem> listNavItems2) {
        super(context2, resLayout2, listNavItems2);
        this.context = context2;
        this.resLayout = resLayout2;
        this.listNavItems = listNavItems2;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View inflate = View.inflate(this.context, this.resLayout, null);
        NavItem navItem = this.listNavItems.get(position);
        ((TextView) inflate.findViewById(R.id.tittle)).setText(navItem.getTitle());
        ((TextView) inflate.findViewById(R.id.sub_tittle)).setText(navItem.getSubtitle());
        ((ImageView) inflate.findViewById(R.id.icon)).setImageResource(navItem.getResIcons());
        return inflate;
    }
}
