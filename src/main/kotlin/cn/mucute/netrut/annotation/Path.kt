package cn.mucute.netrut.annotation

@Target(AnnotationTarget.ANNOTATION_CLASS,AnnotationTarget.FUNCTION, AnnotationTarget.CLASS,AnnotationTarget.TYPE)
@Retention()
annotation class Path(val s: String)