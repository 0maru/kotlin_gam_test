package com.example.gamtest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.doubleclick.PublisherAdRequest
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

const val TEST_AD_UNIT_ID = "/6499/example/rewarded-video"

class MainActivity : AppCompatActivity() {
    private var mIsLoading = false
    private lateinit var mRewardedAds: RewardedAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}
        onLoadRewardAd()
    }

    fun clickOnButton(view: View) {
        showRewardedVideo()
    }

    private  fun onLoadRewardAd() {
        if (!(::mRewardedAds.isInitialized) || !mRewardedAds.isLoaded) {
            mIsLoading = true
            mRewardedAds = RewardedAd(this, TEST_AD_UNIT_ID)
            mRewardedAds.loadAd(
                PublisherAdRequest.Builder().build(),
                object : RewardedAdLoadCallback() {
                    override fun onRewardedAdLoaded() {
                        super.onRewardedAdLoaded()
                        mIsLoading = false
                    }

                    override fun onRewardedAdFailedToLoad(errorCode: Int) {
                        super.onRewardedAdFailedToLoad(errorCode)
                        mIsLoading = false
                    }
                }
            )
        }
    }

    private  fun showRewardedVideo() {
        // 動画リワード読み込み確認
        if (mRewardedAds.isLoaded) {
            mRewardedAds.show(this, object : RewardedAdCallback() {
                override fun onUserEarnedReward(p0: RewardItem) {
                    TODO("not implemented")
                    print("リワード付与")//To change body of created functions use File | Settings | File Templates.
                }

                override fun onRewardedAdClosed() {
                    super.onRewardedAdClosed()
                    onLoadRewardAd()
                }

                override fun onRewardedAdFailedToShow(p0: Int) {
                    super.onRewardedAdFailedToShow(p0)
                }

                override fun onRewardedAdOpened() {
                    super.onRewardedAdOpened()
                }
            })
        } else {
            // 読み込み出来ていない
            // リトライするかリトライさせるか
        }
    }
}
