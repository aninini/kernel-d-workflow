/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package cn.stylefeng.roses.kernel.workflow.api.pojo;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础工作流参数
 *
 * @author fengshuonan
 * @date 2021/6/19 17:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseWorkflowRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 参数校验分组：强退
     */
    public @interface force {
    }

    /**
     * 参数校验分组：停用
     */
    public @interface stop {
    }

    /**
     * 参数校验分组：启用
     */
    public @interface start {
    }

    /**
     * 参数校验分组：部署
     */
    public @interface deploy {
    }

    /**
     * 参数校验分组：挂起
     */
    public @interface suspend {
    }

    /**
     * 参数校验分组：激活
     */
    public @interface active {
    }

    /**
     * 参数校验分组：委托
     */
    public @interface entrust {
    }

    /**
     * 参数校验分组：转办
     */
    public @interface turn {
    }

    /**
     * 参数校验分组：追踪
     */
    public @interface trace {
    }

    /**
     * 参数校验分组：跳转
     */
    public @interface jump {
    }

    /**
     * 参数校验分组：提交
     */
    public @interface submit {
    }

    /**
     * 参数校验分组：退回
     */
    public @interface back {
    }

    /**
     * 参数校验分组：终止
     */
    public @interface end {
    }

    /**
     * 参数校验分组：导出
     */
    public @interface export {
    }

    /**
     * 参数校验分组：映射
     */
    public @interface mapping {
    }

    /**
     * 参数校验分组：切换
     */
    public @interface change {
    }

    /**
     * 参数校验分组：历史审批记录
     */
    public @interface commentHistory {
    }

    /**
     * 参数校验分组：修改状态
     */
    public @interface changeStatus {
    }

    /**
     * 参数校验分组：加签
     */
    public @interface addSign {
    }

    /**
     * 参数校验分组：减签
     */
    public @interface deleteSign {
    }

}
