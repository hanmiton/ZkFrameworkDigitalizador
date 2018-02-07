package zkexample.pagecontrollers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.zkoss.essentials.services.SidebarPage;
import org.zkoss.essentials.services.SidebarPageConfig;

public class SidebarPageConfigImpl implements SidebarPageConfig{
	
	HashMap<String,SidebarPage> pageMap = new LinkedHashMap<String,SidebarPage>();
	public SidebarPageConfigImpl(){		
		pageMap.put("github",new SidebarPage("Github","Código Fuente","/images/github_alt.png","https://github.com/hanmiton/zkFrameworkInsert"));
		pageMap.put("fn1",new SidebarPage("fn1","Creación de usuarios","/images/createUser.png"
				,"/userList.zul"));
		//pageMap.put("fn2",new SidebarPage("fn2","Profile (MVVM)","/images/fn.png"
		//		,"/index-profile-mvvm.zul"));
		pageMap.put("fn3",new SidebarPage("fn3","Digitalización","/images/doc.png"
				,"/fileList.zul"));
		
		//pageMap.put("fn4",new SidebarPage("fn4","Todo List (MVVM)","/images/fn.png"
		//		,"/index-todolist-mvvm.zul"));
	}
	
	public List<SidebarPage> getPages(){
		return new ArrayList<SidebarPage>(pageMap.values());
	}
	
	public SidebarPage getPage(String name){
		return pageMap.get(name);
	}
	
}