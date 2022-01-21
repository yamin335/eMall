package com.rtchubs.arfixture.sceneform

data class Size (var width: Float, var height: Float) {

    operator fun plus(another: Size): Size {
        return Size(this.width + another.width, this.height + another.height)
    }

    operator fun minus(another: Size): Size {
        return Size(this.width - another.width, this.height - another.height)
    }

    operator fun unaryMinus(): Size {
        return Size(-this.width, -this.height)
    }

    operator fun div(target: Size): Size {
        return Size(this.width / target.width, this.height / target.height)
    }

}