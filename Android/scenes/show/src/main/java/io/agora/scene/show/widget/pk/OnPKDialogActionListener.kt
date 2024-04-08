package io.agora.scene.show.widget.pk

/**
 * On p k dialog action listener
 *
 * @constructor Create empty On p k dialog action listener
 */
interface OnPKDialogActionListener {
    /**
     * On request message refreshing
     *
     * @param dialog
     */
    fun onRequestMessageRefreshing(dialog: LivePKDialog)

    /**
     * On invite button chosen
     *
     * @param dialog
     * @param roomItem
     */
    fun onInviteButtonChosen(dialog: LivePKDialog, roomItem: LiveRoomConfig)

    fun onCancelButtonChosen(dialog: LivePKDialog, roomItem: LiveRoomConfig)

    /**
     * On stop p king chosen
     *
     * @param dialog
     */
    fun onStopPKingChosen(dialog: LivePKDialog)
}