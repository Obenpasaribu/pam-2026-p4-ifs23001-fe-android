package org.delcom.pam_p4_ifs23001.helper

class ConstHelper {
    // Route Names
    enum class RouteNames(val path: String) {
        Home(path = "home"),
        Profile(path = "profile"),
        Plants(path = "plants"),
        Plantspc(path = "plantspc"),
        PlantsAddpc(path = "plants/add"),
        PlantsDetailpc(path = "plants/{plantId}"),
        PlantsEditpc(path = "plants/{plantId}/edit"),
        PlantsAdd(path = "plants/add"),
        PlantsDetail(path = "plants/{plantId}"),
        PlantsEdit(path = "plants/{plantId}/edit"),

    }
}