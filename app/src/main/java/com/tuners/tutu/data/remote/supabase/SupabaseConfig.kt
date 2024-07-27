package com.tuners.tutu.data.remote.supabase

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.serializer.JacksonSerializer

class SupabaseConfig {
    companion object {
        private const val SUPABASE_URL = "https://ipxppqajryhicuiigiaa.supabase.co"
        private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlweHBwcWFqcnloaWN1aWlnaWFhIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjAwODUyMDgsImV4cCI6MjAzNTY2MTIwOH0.9oGZMzp-QKx8xx6QZEcIoxSdrQAj6VQ2HqJT8YNYyHo"

        fun getSupabaseClient() = createSupabaseClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_KEY
        ) {
            install(Postgrest)
            install(Realtime)
            defaultSerializer = JacksonSerializer()
        }
    }
}