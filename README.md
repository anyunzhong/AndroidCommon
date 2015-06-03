# AndroidCommon
快速开发android app，封装了android开发常用的功能


# 核心功能

增加类似iOS的Controller

增加TabelViewController 及 TabelView

TableView支持多种下拉刷新风格:
PullDown, PullToRefresh, SwipeRefresh, UltraPullToRefresh, DropDown, BGANormal, BGAMooc, BGAStickiness

集成http请求服务DataService 自动处理各种异常
http接口协议请参考 x-common项目 当然你也可以灵活定制


# 快速开始

public class ShopListController extends TableViewController<Shop> implements
		DataServiceDelegate {

  //数据列表
	private List<Shop> shopList;

  // http请求
	private ShopListDataService dataService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		dataService = new ShopListDataService();
		dataService.setDelegate(this);
		shopList = dataService.getShopList();

		super.onCreate(savedInstanceState);

    //开始加载数据
		dataService.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.shop_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

  //设置下来刷新的类型 目前不是所有类型都支持上拉更多
	@Override
	protected RefreshControlType getRefreshControlType() {
		return RefreshControlType.BGANormal;

	}

  // tableView的总行数
	@Override
	public int getRows() {
		return shopList.size();
	}

  // 返回某一行具体的cell 已经重用
	@Override
	public TableViewCell<Shop> getTableViewCell(int row) {
		return new ShopListTableViewCell(R.layout.shop_list,
				LayoutInflater.from(this));
	}

	@Override
	public void onClickRow(int row) {

	}

  //返回具体某一行需要显示的数据
	@Override
	public Shop getEntity(int row) {
		return shopList.get(row);
	}

  //下来刷新回调
	@Override
	public void onRefresh() {

		if (shopList != null) {
			shopList.clear();
		}
		dataService.execute();

	}


	@Override
	public void onLoadMore() {
		dataService.execute();
	}

	@Override
	public void onStatusOk(BaseResponse response, Class<?> type) {

		if (shopList.size() > 4) {
			loadOver(true);
		} else {
			loadOver(false);
		}

		super.onStatusOk(response, type);
		Log.e("response", response.toString());

	}

	@Override
	protected boolean enableAutoLoadMore() {
		return true;
	}

}
