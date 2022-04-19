package id.go.blitarkab.bpbd.domain.model.common

interface Mapper<Domain, Model> {
    fun toDomain(model: Model): Domain
    fun fromDomain(domain: Domain): Model
    fun toDomainList(models: List<Model>): List<Domain> = models.map { toDomain(it) }
    fun fromDomainList(domains: List<Domain>): List<Model> = domains.map { fromDomain(it) }
}