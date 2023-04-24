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
using System.Linq;

namespace CommonWallet
{
    public static class LatestPayments
    {
        private static readonly List<Payment> latestPayments = new List<Payment>
        {
            new Payment { PaymentId = 22, PayerName = "Vuk Bibic", Amount = 50, Description = "Mensa", PaymentTime = DateTime.Now },
            new Payment { PaymentId = 21, PayerName = "Lazar Minic", Amount = 30, Description = "HIT", PaymentTime = DateTime.Now.AddDays(-2) },
            new Payment { PaymentId = 20, PayerName = "Uros Stojkovic", Amount = 20, Description = "looooooooooooooooooooooooooooonggg descriptiooooooooon", PaymentTime= DateTime.Now.AddDays(-3) },
        };

        [FunctionName("LatestPayments")]
        public static async Task<IActionResult> Run(
            [HttpTrigger(AuthorizationLevel.Function, "get", Route = "wallet/{wallet_id}/payments")] HttpRequest req,
			[Sql(commandText: "SELECT TOP(CAST(@n AS INT)) pay.payment_id AS payment_id, p.first_name AS payer_name, pay.description AS description, pay.amount AS amount, pay.payment_time AS payment_time FROM payment pay, person p where wallet_id = @WalletId AND pay.payer_id = p.person_id ORDER BY pay.payment_time DESC",
				commandType: System.Data.CommandType.Text,
				parameters: "@WalletId={wallet_id},@n={Query.n}",
				connectionStringSetting: "SqlConnectionString")] IEnumerable<Payment> latestPayments,
			ILogger log)
		{
            log.LogInformation("LatestPayments function processed a request.");
            log.LogInformation($"Latest payments: {latestPayments}");
            return new OkObjectResult(latestPayments);
        }
    }
}
