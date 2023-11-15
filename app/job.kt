data class Job(val title: String, val company: String, val location: String, val description: String)

val jobsList = listOf(
    Job("Software Engineer", "Company A", "New York", "Design and develop software applications."),
    Job("Product Manager", "Company B", "Los Angeles", "Plan, develop, and launch new products."),
    Job("UX Designer", "Company C", "San Francisco", "Design user interfaces that meet usability and accessibility standards."),
    // add more jobs here
)


fun searchJobs(query: String, jobs: List<Job>): List<Job> {
    return jobs.filter { job ->
        job.title.contains(query, ignoreCase = true) ||
                job.company.contains(query, ignoreCase = true) ||
                job.location.contains(query, ignoreCase = true) ||
                job.description.contains(query, ignoreCase = true)
    }
}

class JobsAdapter(private val jobs: List<Job>) : RecyclerView.Adapter<JobsAdapter.JobViewHolder>() {

    inner class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val jobTitle: TextView = itemView.findViewById(R.id.job_title)
        val jobCompany: TextView

        class JobsAdapter(private val jobs: List<Job>) : RecyclerView.Adapter<JobsAdapter.JobViewHolder>() {

            inner class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                val jobTitle: TextView = itemView.findViewById(R.id.job_title)
                val jobCompany: TextView = itemView.findViewById(R.id.job_company)
                val jobLocation: TextView = itemView.findViewById(R.id.job_location)
                val jobDescription: TextView = itemView.findViewById(R.id.job_description)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.job_item, parent, false)
                return JobViewHolder(view)
            }

            override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
                val job = jobs[position]
                holder.jobTitle.text = job.title
                holder.jobCompany.text = job.company
                holder.jobLocation.text = job.location
                holder.jobDescription.text = job.

                val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = JobsAdapter(jobsList)