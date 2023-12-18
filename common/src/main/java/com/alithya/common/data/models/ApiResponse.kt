package com.alithya.common.data.models

import com.alithya.common.domain.models.InfoUser
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("jsonapi") val jsonapi: Jsonapi,
    @SerializedName("data") val data: Data,
    @SerializedName("links") val links: Links,
)

// ApiResponse convert to InfoUser
fun ApiResponse.toDomain(): InfoUser {
    return InfoUser(
        id = data.id,
        name = data.attributes.name,
        email = data.attributes.mail,
        firstName = data.attributes.fieldFirstname,
        lastName = data.attributes.fieldLastname,
        displayName = data.attributes.displayName,
        jobTitle = data.attributes.fieldJob,
        managerFullName = data.attributes.fieldUserManagerFullname,
        imageUrl = data.attributes.fieldProfileImage,
    )
}

data class Jsonapi(
    @SerializedName("meta") val meta: Meta,
    @SerializedName("version") val version: String
)

data class Data(
    @SerializedName("attributes") val attributes: Attributes,
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String
)

data class Links(
    @SerializedName("self") val self: Self
)

data class Self(
    @SerializedName("href") val href: String
)

data class Meta(
    @SerializedName("links") val links: Links
)

data class Trainings(
    @SerializedName("first_training_in_progress") val firstTrainingInProgress: Any,
    @SerializedName("trainings_completed") val trainingsCompleted: List<LanguageInfo>,
    @SerializedName("trainings_in_progress") val trainingsInProgress: List<LanguageInfo>,
    @SerializedName("trainings_plan") val trainingsPlan: List<LanguageInfo>,
    @SerializedName("trainings_plan_by_skill") val trainingsPlanBySkill: List<SkillData>,
    @SerializedName("trainings_to_follow") val trainingsToFollow: List<LanguageInfo>,
    @SerializedName("trainings_to_follow_by_skill") val trainingsToFollowBySkill: List<LanguageInfo>
)

data class LanguageInfo(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("langcode") val langCode: String,
)

data class SkillData(
    @SerializedName("skill") val skill: Skill,
    @SerializedName("formationsIds") val formationIds: List<String>
)

data class Skill(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("tid") val tid: Int,
    @SerializedName("name") val name: String,
    @SerializedName("langcode") val langCode: String,
    @SerializedName("level_class") val levelClass: String?,
    @SerializedName("flag_class") val flagClass: String?
)

data class LanguageInfoWithYears(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("langcode") val langCode: String,
    @SerializedName("years_of_use") val yearsOfUse: String?,
)

data class Attributes(
    @SerializedName("access") val access: String,
    @SerializedName("changed") val changed: String,
    @SerializedName("created") val created: String,
    @SerializedName("default_langcode") val defaultLangcode: String,
    @SerializedName("displayName") val displayName: String,
    @SerializedName("drupal_internal__uid") val drupalInternalUid: String,
    @SerializedName("field_firstname") val fieldFirstname: String,
    @SerializedName("field_job") val fieldJob: String,
    @SerializedName("field_job_tid") val fieldJobTid: String,
    @SerializedName("field_job_uuid") val fieldJobUuid: String,
    @SerializedName("field_lastname") val fieldLastname: String,
    @SerializedName("field_profile_image") val fieldProfileImage: String,
    @SerializedName("field_user_manager_fullname") val fieldUserManagerFullname: String,
    @SerializedName("field_user_manager_uid") val fieldUserManagerUid: String,
    @SerializedName("init") val `init`: String? = null,
    @SerializedName("langcode") val langcode: String,
    @SerializedName("login") val login: String,
    @SerializedName("mail") val mail: String,
    @SerializedName("name") val name: String,
    @SerializedName("percentage_of_completion") val percentageOfCompletion: String,
    @SerializedName("preferred_admin_langcode") val preferredAdminLangcode: String,
    @SerializedName("preferred_langcode") val preferredLangcode: String,
    @SerializedName("roles") val roles: List<String>,
    @SerializedName("skills") val skills: List<LanguageInfo>,
    @SerializedName("skills_and_experiences") val skillsAndExperiences: List<LanguageInfoWithYears>,
    @SerializedName("status") val status: String,
    @SerializedName("team") val team: List<String>,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("token") val token: String,
    @SerializedName("trainings") val trainings: Trainings
)