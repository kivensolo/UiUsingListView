package com.kingz.base.response

data class ResponseResult<out T>(
    val code: Int?,
    val message: String?,
    val data: T?,
    val status: Status
) {

    companion object {

        fun <T> response(data: T?): ResponseResult<T> {
            if (data != null) {
                if (true) { // TODO 判断数据code是不是业务code
                    return success(data)
                } else { // 业务不正常的code
                    return failure(data, "错误信息从返回中拿")
                }
            }
            return error("接口数据异常", null)
        }

        fun <T> success(data: T?): ResponseResult<T> {
            return ResponseResult(0,null, data,  Status.SUCCESS)
        }

        fun <T> failure(data: T?, msg: String): ResponseResult<T> {
            return ResponseResult(-1,msg, data,  Status.FAILURE)
        }

        fun <T> error(msg: String, data: T? = null): ResponseResult<T> {
            return ResponseResult(-1,msg, data,  Status.ERROR)
        }

        fun <T> loading(): ResponseResult<T> {
            return ResponseResult(-2,null, null,  Status.LOADING)
        }

        fun <T> loading(data: T?): ResponseResult<T> {
            return ResponseResult(-2,null, data,  Status.LOADING)
        }
    }

}