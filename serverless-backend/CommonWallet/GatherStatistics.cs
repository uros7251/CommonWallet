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
using CommonWallet.Models;

namespace CommonWallet
{
    public static class GatherStatistics
    {
        private static readonly List<TotalAndNetPayments> totalAndNetPayments = new List<TotalAndNetPayments>
        {
            new TotalAndNetPayments { PersonId = 1, Name = "Uros Stojkovic", Total = 80, Net = 10 },
            new TotalAndNetPayments { PersonId = 2, Name = "Vuk Bibic", Total = 60, Net = -20 },
            new TotalAndNetPayments { PersonId = 3, Name = "Lazar Minic", Total = 70, Net = 10}
        };

        [FunctionName("GatherStatistics")]
        public static async Task<IActionResult> Run(
            [HttpTrigger(AuthorizationLevel.Function, "get", Route = "wallet/{wallet_id}/stats")] HttpRequest req,
            [Sql(commandText: "EXECUTE [dbo].[TotalAndNetPayments] @WalletId",
                commandType: System.Data.CommandType.Text,
                parameters: "@WalletId={wallet_id}",
                connectionStringSetting: "SqlConnectionString")] IEnumerable<TotalAndNetPayments> totalAndNetPayments,
            ILogger log)
        {
            log.LogInformation("GatherStatistics function processed a request.");
            return new OkObjectResult(totalAndNetPayments);
        }
    }
}
