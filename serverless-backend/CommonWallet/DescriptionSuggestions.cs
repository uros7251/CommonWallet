using System;
using System.IO;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Azure.WebJobs;
using Microsoft.Azure.WebJobs.Extensions.Http;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using System.Collections.Generic;

namespace CommonWallet
{
    public static class DescriptionSuggestions
    {
        private static readonly List<string> descriptionSuggestions = new List<string>
        {
			"\uD83D\uDED2 HIT",
			"\uD83C\uDF5B Mensa",
			"\uD83C\uDF2F Eating Out",
			"\uD83C\uDF7B Drinking Out",
			"\u270D\uFE0F Other"
		};

        [FunctionName("DescriptionSuggestions")]
        public static async Task<IActionResult> Run(
            [HttpTrigger(AuthorizationLevel.Function, "get", Route = "wallet/{wallet_id}/descriptions")] HttpRequest req,
            ILogger log)
        {
            log.LogInformation("DescriptionSuggestions function processed a request.");

            return new OkObjectResult(descriptionSuggestions);
        }
    }
}
