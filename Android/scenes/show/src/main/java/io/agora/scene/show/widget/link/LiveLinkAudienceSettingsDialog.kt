package io.agora.scene.show.widget.link

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import io.agora.scene.show.R
import io.agora.scene.show.databinding.ShowWidgetLinkSettingsDialogBinding
import io.agora.scene.show.databinding.ShowWidgetSettingDialogItemBinding
import io.agora.scene.show.widget.BottomDarkDialog
import io.agora.scene.widget.basic.BindingSingleAdapter
import io.agora.scene.widget.basic.BindingViewHolder

/**
 * Live link audience settings dialog
 *
 * @constructor
 *
 * @param context
 */
class LiveLinkAudienceSettingsDialog constructor(context: Context) : BottomDarkDialog(context) {
    companion object {
        /**
         * Item Id Mic
         */
        const val ITEM_ID_MIC = 1

        /**
         * Item Id Stop Link
         */
        const val ITEM_ID_STOP_LINK = 2

        @IntDef(
            ITEM_ID_MIC,
            ITEM_ID_STOP_LINK
        )
        @Retention(AnnotationRetention.RUNTIME)
        @Target(
            AnnotationTarget.TYPEALIAS,
            AnnotationTarget.FUNCTION,
            AnnotationTarget.PROPERTY_GETTER,
            AnnotationTarget.PROPERTY_SETTER,
            AnnotationTarget.VALUE_PARAMETER,
            AnnotationTarget.FIELD,
            AnnotationTarget.LOCAL_VARIABLE
        )
        annotation class ItemId
    }

    /**
     * Setting item
     *
     * @property itemId
     * @property icon
     * @property activatedIcon
     * @property text
     * @property activatedText
     * @property activated
     * @constructor Create empty Setting item
     */
    private data class SettingItem(
        @ItemId val itemId: Int,
        @DrawableRes val icon: Int,
        @DrawableRes val activatedIcon: Int,
        @StringRes val text: Int,
        @StringRes val activatedText: Int,
        var activated: Boolean = false
    )

    /**
     * M host item list
     */
    private val mHostItemList = listOf(
        SettingItem(
            ITEM_ID_MIC,
            R.mipmap.show_setting_ic_mic_off,
            R.mipmap.show_setting_ic_mic_on,
            R.string.show_setting_mic_off,
            R.string.show_setting_mic_on,
            true
        ),
        SettingItem(
            ITEM_ID_STOP_LINK,
            R.mipmap.show_stop_link,
            R.mipmap.show_stop_link,
            R.string.show_stop_linking,
            R.string.show_stop_linking
        )
    )

    /**
     * On item activated change listener
     */
    private var onItemActivatedChangeListener: ((dialog: LiveLinkAudienceSettingsDialog, itemId: Int, activated: Boolean)->Unit)? = null

    /**
     * M binding
     */
    private val mBinding by lazy {
        ShowWidgetLinkSettingsDialogBinding.inflate(
            LayoutInflater.from(
                context
            )
        )
    }

    /**
     * M adapter
     */
    private val mAdapter by lazy {
        object : BindingSingleAdapter<SettingItem, ShowWidgetSettingDialogItemBinding>() {
            override fun onBindViewHolder(
                holder: BindingViewHolder<ShowWidgetSettingDialogItemBinding>,
                position: Int
            ) {
                val item = getItem(position) ?: return
                val activated = item.activated
                holder.binding.text.setCompoundDrawables(
                    null,
                    context.getDrawable(if (activated) item.activatedIcon else item.icon)?.apply {
                        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
                    },
                    null, null,
                )
                holder.binding.text.text =
                    context.getString(if (activated) item.activatedText else item.text)
                holder.binding.text.isActivated = activated
                holder.binding.text.setOnClickListener {
                    val activate = !it.isActivated
                    it.isActivated = activate
                    item.activated = activate

                    holder.binding.text.setCompoundDrawables(
                        null,
                        context.getDrawable(if (activate) item.activatedIcon else item.icon)
                            ?.apply {
                                setBounds(0, 0, intrinsicWidth, intrinsicHeight)
                            },
                        null, null,
                    )
                    holder.binding.text.text =
                        context.getString(if (activate) item.activatedText else item.text)

                    onItemActivatedChangeListener?.invoke(
                        this@LiveLinkAudienceSettingsDialog,
                        item.itemId,
                        activate
                    )
                }
            }
        }
    }

    init {
        setBottomView(mBinding.root)
        mBinding.recycleView.adapter = mAdapter
        mAdapter.resetAll(mHostItemList)
    }

    /**
     * Reset settings item
     *
     * @param mute
     */
    fun resetSettingsItem(mute: Boolean) {
        val itemList = listOf(
            SettingItem(
                ITEM_ID_MIC,
                R.mipmap.show_setting_ic_mic_off,
                R.mipmap.show_setting_ic_mic_on,
                R.string.show_setting_mic_off,
                R.string.show_setting_mic_on,
                !mute
            ),
            SettingItem(
                ITEM_ID_STOP_LINK,
                R.mipmap.show_stop_link,
                R.mipmap.show_stop_link,
                R.string.show_stop_linking,
                R.string.show_stop_linking
            ))
        mAdapter.resetAll(itemList)
    }

    /**
     * Set audience info
     *
     * @param userName
     */
    fun setAudienceInfo(userName : String) {
        mBinding.userName.text = context.getString(R.string.show_to_the_audience,userName)
    }

    /**
     * Set on item activate changed listener
     *
     * @param listener
     * @receiver
     */
    fun setOnItemActivateChangedListener(listener: (dialog: LiveLinkAudienceSettingsDialog, itemId: Int, activated: Boolean)->Unit) {
        this.onItemActivatedChangeListener = listener
    }
}