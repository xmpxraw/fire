<template>
    <div class="noClass-main">
        <div class="noClass-header">
            <div class="noClassDescribe">
                <div class="describe"><span>温馨提示</span></div>
            </div>
        </div>
        <div class="noClass-contain">
            暂无报班信息,请检查您是否已经预约！
        </div>
        <div class="j-cancle" @click='cancle'>关闭页面</div>
    </div>
</template>
<script>
import Dialog from 'vux-components/dialog'
import XButton from 'vux-components/x-button'
import loading from 'vux-components/loading'
import toast from 'vux-components/toast'
export default {
    name: 'noClass',
    components: {
        Dialog,
        XButton,
        loading
    },
    ready() {
        this.powerState(); //监听返回键
    },
    data() {
        return {
            showNoScroll: false,
            showitem: true,
            noClassData: [],
            loading: true,
        }
    },
    methods: {
        cancle: function() {
            wx.closeWindow();
        },
        powerState: function() {
            var state = {
                title: "title",
                url: "#"
            };
            window.history.pushState(state, "title", "#");
            window.addEventListener("popstate", function(e) {
                wx.closeWindow();
            }, false);
        },
    },
}
</script>
<style lang="scss">
.noClass-main {
    background: #ebebeb;
    height: auto;
    padding-bottom: 2.3rem;
}

.noClass-header {
    width: 100%;
    padding: 0 .5rem;
    height: 2.95rem;
    background: #f6f5f5;
    -webkit-box-shadow: 0px 3px 5px #ccc;
    box-shadow: 0px 3px 5px #ccc;
    .noClassDescribe {
        width: 100%;
        height: 100%;
        line-height: 2.95rem;
        background: url(../assets/images/line.png) repeat-x center;
    }
    .describe {
        width: 6rem;
        height: 100%;
        line-height: 2.95rem;
        background: #f6f5f5;
        font-size: .9rem;
        color: #438ac9;
        font-weight: bold;
    }
    .describe span {
        display: inline-block;
        width: 100%;
        height: 2.95rem;
        line-height: 2.95rem;
        background: url(../assets/images/icon_face.png) no-repeat .5rem .85rem;
        background-size: 1.1rem;
        padding-left: 1.9rem;
    }
}

.noClass-contain {
    width: 100%;
    font-size: .85rem;
    padding: .5rem;
    line-height: 1.5rem;
    color: #333;
}

.noClass-main .j-cancle {
    width: 90%;
    height: 2.5rem;
    line-height: 2.5rem;
    text-align: center;
    color: #fff;
    font-size: .9rem;
    background: #4389ca;
    border-radius: .25rem;
    position: absolute;
    bottom: 10rem;
    left: 50%;
    margin-left: -45%;
}
</style>
