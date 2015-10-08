/**
 * Created by sun.mt on 2015/9/25.
 */

/**
 * 提交表单
 * @param el
 * @param action
 */
function submitForm(el, action){
    $(el).closest('form').attr('action', action).submit()
}
