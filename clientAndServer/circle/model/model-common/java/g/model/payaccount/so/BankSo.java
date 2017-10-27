package g.model.payaccount.so;

import g.model.payaccount.po.Bank;

import java.util.List;


/**
 * 银行表查询对象
 *
 * @author mark
 * @time 2016-7-13 19:21:26
 */
//region your codes 1
public class BankSo extends Bank {
//endregion your codes 1

	//region your codes 3
	private static final long serialVersionUID = -8345799300489278424L;
	//endregion your codes 3

	//region your codes 2
	private List<String> bankDistrictList;

	public List<String> getBankDistrictList() {
		return bankDistrictList;
	}

	public void setBankDistrictList(List<String> bankDistrictList) {
		this.bankDistrictList = bankDistrictList;
	}
	//endregion your codes 2
}