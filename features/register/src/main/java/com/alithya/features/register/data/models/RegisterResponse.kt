package com.alithya.features.register.data.models

import com.google.gson.annotations.SerializedName

internal data class RegisterResponse(
    @SerializedName("access")
    val access: List<Access>,
    @SerializedName("changed")
    val changed: List<Changed>,
    @SerializedName("created")
    val created: List<Created>,
    @SerializedName("default_langcode")
    val defaultLangcode: List<DefaultLangcode>,
    @SerializedName("field_firstname")
    val fieldFirstname: List<FieldFirstname>,
    @SerializedName("field_job")
    val fieldJob: List<FieldJob>,
    @SerializedName("field_lastname")
    val fieldLastname: List<FieldLastname>,
    @SerializedName("field_profile_image")
    val fieldProfileImage: List<Any>,
    @SerializedName("field_user_manager")
    val fieldUserManager: List<Any>,
    @SerializedName("field_user_skills_and_experience")
    val fieldUserSkillsAndExperience: List<Any>,
    @SerializedName("field_user_trainings_completed")
    val fieldUserTrainingsCompleted: List<Any>,
    @SerializedName("field_user_trainings_in_progress")
    val fieldUserTrainingsInProgress: List<Any>,
    @SerializedName("field_user_trainings_plan")
    val fieldUserTrainingsPlan: List<Any>,
    @SerializedName("field_user_trainings_to_follow")
    val fieldUserTrainingsToFollow: List<Any>,
    @SerializedName("init")
    val `init`: List<Any>,
    @SerializedName("langcode")
    val langcode: List<Langcode>,
    @SerializedName("login")
    val login: List<Login>,
    @SerializedName("mail")
    val mail: List<Mail>,
    @SerializedName("name")
    val name: List<Name>,
    @SerializedName("path")
    val path: List<Path>,
    @SerializedName("preferred_admin_langcode")
    val preferredAdminLangcode: List<Any>,
    @SerializedName("preferred_langcode")
    val preferredLangcode: List<PreferredLangcode>,
    @SerializedName("roles")
    val roles: List<Any>,
    @SerializedName("status")
    val status: List<Status>,
    @SerializedName("timezone")
    val timezone: List<Timezone>,
    @SerializedName("uid")
    val uid: List<Uid>,
    @SerializedName("uuid")
    val uuid: List<Uuid>
)
