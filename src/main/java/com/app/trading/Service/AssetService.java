package com.app.trading.Service;

import java.util.List;

import com.app.trading.Domain.Asset;
import com.app.trading.Model.Coin;
import com.app.trading.Model.User;

public interface AssetService {
	
	Asset createAsset(User user , Coin coin , double quantity);
	
	Asset getAssetById(Long assertId) throws Exception;
	
	Asset getAssetByUserIdAndId(Long userId,Long assetId);
	
	List<Asset>getUsersAssets(Long userId);
	
	Asset updateAsset(Long assetId , double quantity ) throws Exception;
	
	Asset findAssetByUserIdAndCoinId(Long userId , String coinId);
	
	void deleteAsset(Long assetId);

}
