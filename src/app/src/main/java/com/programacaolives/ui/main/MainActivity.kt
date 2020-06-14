package com.programacaolives.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.programacaolives.R
import com.programacaolives.base.BaseActivity
import com.programacaolives.compat.ConnectivityManagerCompat
import com.programacaolives.configuration.AppDependencyInjector
import com.programacaolives.domain.entity.Live
import com.programacaolives.log.Log
import com.programacaolives.utils.date.DateServiceImpl
import com.programacaolives.utils.rx.AppSchedulerProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.shimmer_placeholder_layout.*

class MainActivity : BaseActivity<MainMvpPresenter>(), MainMvpView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initializePresenter(
            MainPresenter(
                this,
                AppDependencyInjector(DateServiceImpl(), getString(R.string.base_url)),
                AppSchedulerProvider()
            )
        )

        val isOnline = ConnectivityManagerCompat.isConnected(this)
        presenter.onCreate(isOnline)
    }

    override fun onResume() {
        super.onResume()

        if (!isCreatingActivity && !isActivitySetup) {
            val isOnline = ConnectivityManagerCompat.isConnected(this)
            presenter.onCreate(isOnline)
        }

        isCreatingActivity = false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        presenter.onOptionsItemSelected(item.itemId)

    override fun setupMainNormalState(lives: List<Live>) {
        setupVisibilityComponents(View.VISIBLE, View.GONE, View.VISIBLE)

        val linearLayoutManager = LinearLayoutManager(this)
        val liveAdapter = LiveAdapter(lives)

        list_lives.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = liveAdapter
        }

        isActivitySetup = true
    }

    override fun setupMainEmptyState() {
        setupVisibilityComponents(View.VISIBLE, View.VISIBLE, View.GONE)
        isActivitySetup = true
    }

    override fun setupMainErrorState() {
        setupVisibilityComponents(View.GONE)
        showMessageError()
        isActivitySetup = true
    }

    private fun setupVisibilityComponents(
        mainContainerVisibility: Int,
        txtEmptyStateVisibility: Int = View.GONE,
        listLivesVisibility: Int = View.GONE
    ) {
        hideShimmerView()
        main_view_container.visibility = mainContainerVisibility
        txt_empty_state.visibility = txtEmptyStateVisibility
        list_lives.visibility = listLivesVisibility
    }

    override fun initializeActivityWithInternet() {
        main_view_container.visibility = View.GONE
        shimmer_view_container.visibility = View.VISIBLE
        shimmer_view_container.startShimmer()
    }

    override fun initializeActivityWithoutInternet() {
        hideShimmerView()
        main_view_container.visibility = View.GONE
        showNetworkErrorMessage()
    }

    override fun navigateToAppReview(url: String) {
        val reviewPage = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, reviewPage)

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            val message = getString(R.string.rate_error_message)
            Log.log(message)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun navigateToSharingScreen(url: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, getString(applicationInfo.labelRes))
            val text = getString(R.string.share_app_message)
            putExtra(Intent.EXTRA_TEXT, "$text $url")
        }

        startActivity(Intent.createChooser(intent, getString(R.string.share_link)))
    }

    private fun hideShimmerView() {
        shimmer_view_container.stopShimmer()
        shimmer_view_container.visibility = View.GONE
    }

    private fun showNetworkErrorMessage() {
        Toast.makeText(
            this,
            getString(R.string.network_error_message),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onStop() {
        super.onStop()
        hideShimmerView()
        presenter.onStop()
    }

    private fun showMessageError() {
        Toast.makeText(
            this,
            getString(R.string.generic_error_message),
            Toast.LENGTH_SHORT
        ).show()
    }
}