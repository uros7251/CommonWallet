using System;
using System.IO;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Azure.WebJobs;
using Microsoft.Azure.WebJobs.Extensions.Http;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using CommonWallet.Models;
using System.Collections.Generic;

namespace CommonWallet
{
    public static class WalletParticipants
    {
        private static readonly List<Payer> participants = new List<Payer>
        {
            new Payer { Id = 1, Name = "Uros Stojkovic" },
            new Payer { Id = 2, Name = "Vuk Bibic" },
            new Payer { Id = 3, Name = "Lazar Minic"}
        };

		[FunctionName("WalletParticipants")]
        public static async Task<IActionResult> Run(
            [HttpTrigger(AuthorizationLevel.Function, "get", Route = "wallet/{wallet_id}/participants")] HttpRequest req,
			//[Sql(commandText: "SELECT p.person_id as id, p.first_name + ' ' + p.last_name as name FROM Person p, WalletToPerson w2p WHERE w2p.wallet_id = @WalletId AND w2p.person_id = p.person_id",
			//	commandType: System.Data.CommandType.Text,
			//	parameters: "@WalletId={wallet_id}",
			//	connectionStringSetting: "SqlConnectionString")] IEnumerable<Payer> participants,
			ILogger log)
        {
            log.LogInformation("WalletParticipants function processed a request.");

            return new OkObjectResult(participants);
        }
    }
}
