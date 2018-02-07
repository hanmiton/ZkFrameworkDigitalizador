package zkexample.zkoss;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.web.theme.StandardTheme.ThemeOrigin;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.theme.Themes;

public class IndexVM {

	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view) {

		Themes.register("bluetheme", ThemeOrigin.FOLDER);
		Themes.register("greentheme", ThemeOrigin.FOLDER);
		Themes.register("browntheme", ThemeOrigin.FOLDER);
		Themes.register("purpletheme", ThemeOrigin.FOLDER);
		Themes.register("redtheme", ThemeOrigin.FOLDER);
		
		String theme = FHSessionUtil.getCurrentUser().getTheme();
		if (!theme.equals(Themes.getCurrentTheme())) {
			Themes.setTheme(Executions.getCurrent(), FHSessionUtil
					.getCurrentUser().getTheme());
		}

		Executions.sendRedirect("main.zul");
	}

}
