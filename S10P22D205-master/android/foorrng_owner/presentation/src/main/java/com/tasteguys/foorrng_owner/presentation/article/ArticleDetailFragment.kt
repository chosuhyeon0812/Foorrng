package com.tasteguys.foorrng_owner.presentation.article

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.tasteguys.foorrng_owner.presentation.R
import com.tasteguys.foorrng_owner.presentation.databinding.FragmentArticleDetailBinding
import com.tasteguys.foorrng_owner.presentation.main.MainBaseFragment
import com.tasteguys.foorrng_owner.presentation.main.MainToolbarControl
import com.tasteguys.foorrng_owner.presentation.model.article.Article
import java.text.SimpleDateFormat
import java.util.Locale

class ArticleDetailFragment(
    private val article: Article
) : MainBaseFragment<FragmentArticleDetailBinding>(
    FragmentArticleDetailBinding::bind, R.layout.fragment_article_detail
) {

    private val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.layout_article_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.layout_article_map, it).commit()
            }
        mapFragment.getMapAsync(mapCallBack)

        initView()
        registerListener()
    }

    private fun initView() {
        binding.tvArticleOrganizer.text = article.organizer
        binding.tvArticleDate.text = "${dateFormat.format(article.startDate)} ~ ${dateFormat.format(article.endDate)}"
        binding.tvArticleCallNumber.text = article.phone
        binding.tvArticleEmail.text = article.email
        binding.tvArticleKakao.text = article.kakaoId
        binding.tvArticleAddress.text = article.address
        Glide.with(binding.root)
            .load(article.mainImage)
            .fitCenter()
            .into(binding.ivArticleDetail)
    }

    private fun registerListener(){
        binding.tvArticleKakao.setOnLongClickListener { view ->
            copyToClipboard(article.kakaoId)
            true
        }
        binding.tvArticleEmail.setOnLongClickListener { view ->
            copyToClipboard(article.email)
            true
        }
        binding.tvArticleCallNumber.setOnLongClickListener { view ->
            copyToClipboard(article.phone)
            true
        }
        binding.tvArticleAddress.setOnLongClickListener {
            copyToClipboard(article.address)
            true
        }
    }

    override fun setToolbar() {
        mainViewModel.changeToolbar(
            MainToolbarControl(
                true,
                article.title,
            ).addNavIconClickListener {
                parentFragmentManager.popBackStack()
            }
        )
    }

    private val mapCallBack = OnMapReadyCallback{ naverMap ->
        naverMap.uiSettings.apply {
            isTiltGesturesEnabled = false
            isRotateGesturesEnabled = false
        }

        naverMap.addOnCameraIdleListener {
            naverMap.moveCamera(
                CameraUpdate.scrollTo(
                    article.latLng
                ).animate(CameraAnimation.Easing).finishCallback {
                    naverMap.moveCamera(
                        CameraUpdate.zoomTo(16.0).animate(CameraAnimation.Easing)
                    )
                }
            )
        }

        Marker().apply {
            position = article.latLng
            icon = OverlayImage.fromResource(R.drawable.ic_marker)
            this.map = naverMap
        }
    }

    private fun copyToClipboard(text: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
        showToast("복사되었습니다 :)")
    }
}