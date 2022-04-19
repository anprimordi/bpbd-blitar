package id.go.blitarkab.bpbd.domain.model

data class Dashboard(
    val covid: Covid,
    val earthQuake: EarthQuake
) {

    companion object {
        val DEFAULT: Dashboard = Dashboard(
            covid = Covid.DEFAULT,
            earthQuake = EarthQuake.DEFAULT
        )
    }

}